package com.hendisantika.sekolah.controller;

import static com.hendisantika.sekolah.enumeration.ALLCONSTANT.KAT_LIST;
import static com.hendisantika.sekolah.enumeration.ALLCONSTANT.RIE_ADMIN_TUL;
import static com.hendisantika.sekolah.enumeration.ALLCONSTANT.TULISAN;
import static com.hendisantika.sekolah.enumeration.ALLCONSTANT.TULISANLIST;
import static com.hendisantika.sekolah.util.WordUtils.pregReplace;
import static com.hendisantika.sekolah.util.WordUtils.stripTags;
import static org.apache.commons.lang3.StringUtils.lowerCase;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.hendisantika.sekolah.entity.Pengguna;
import com.hendisantika.sekolah.entity.Tulisan;
import com.hendisantika.sekolah.repository.KategoriRepository;
import com.hendisantika.sekolah.repository.PenggunaRepository;
import com.hendisantika.sekolah.repository.TulisanRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by IntelliJ IDEA.
 * Project : sekolah
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 25/03/20
 * Time: 08.40
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/tulisan")
@PreAuthorize("hasAuthority('ADMIN')")
public class TulisanController {
  private final TulisanRepository tulisanRepository;
  private final KategoriRepository kategoriRepository;
  private final PenggunaRepository penggunaRepository;

  @GetMapping
  public String tulisan(Model model, Pageable pageable) {
    log.info("Menampilkan data untuk Halaman List Berita.");
    model.addAttribute(TULISANLIST.getDescription(), tulisanRepository.findAll(pageable));
    model.addAttribute("waktu", LocalDateTime.now());

    return "/admin/tulisan/tulisan";
  }

  @GetMapping("/add")
  public String tampilkanFormTulisan(Model model) {
    log.info("Menampilkan Form Tulisan");
    model.addAttribute(KAT_LIST.getDescription(), kategoriRepository.findAll());
    model.addAttribute(TULISAN.getDescription(), new Tulisan());

    return "/admin/tulisan/tulisan-form";
  }

  @GetMapping("/edit/{tulisanId}")
  public String tampilkanFormEditTulisan(Model model, @PathVariable("tulisanId") UUID tulisanId) {
    log.info("Menampilkan Form Edit Tulisan");
    model.addAttribute(KAT_LIST.getDescription(), kategoriRepository.findAll());
    model.addAttribute(TULISAN.getDescription(), tulisanRepository.findById(tulisanId));

    return "/admin/tulisan/tulisan-edit";
  }

  @PostMapping("/edit")
  public String editTulisan(Model model, @Valid Tulisan tulisanBaru, @RequestParam("file") MultipartFile file,
      Principal principal, Pageable pageable, SessionStatus status) {
    log.info("Mengedit Data Tulisan");
    Tulisan tulisanFromDB = tulisanRepository.findById(tulisanBaru.getId()).orElse(null);
    tulisanBaru.setCreatedOn((Objects.requireNonNull(tulisanFromDB).getCreatedOn() == null) ? LocalDateTime.now()
        : tulisanFromDB.getCreatedOn());
    saveDataTulisan(tulisanBaru, file, principal, status);
    model.addAttribute(TULISANLIST.getDescription(), tulisanRepository.findAll(pageable));

    return RIE_ADMIN_TUL.getDescription();
  }

  @PostMapping
  public String tambahTulisan(Model model, Tulisan tulisan, @RequestParam("file") MultipartFile file,
      Principal principal, Pageable pageable, BindingResult errors, SessionStatus status) {
    log.info("Menambahkan Tulisan yang baru");
    if (errors.hasErrors()) {
      log.info("Tambah Tulisan yang baru gagal. {}", errors);
      return "redirect:/admin/tulisan/add";
    }
    saveDataTulisan(tulisan, file, principal, status);
    model.addAttribute(TULISANLIST.getDescription(), tulisanRepository.findAll(pageable));

    return RIE_ADMIN_TUL.getDescription();
  }

  private void saveDataTulisan(Tulisan tulisan, @RequestParam("file") MultipartFile file, Principal principal,
      SessionStatus status) {
    try {
      // Get the file and save it somewhere
      byte[] bytes = file.getBytes();
      String encoded = Base64.getEncoder().encodeToString(bytes);
      String username = principal.getName();
      Pengguna pengguna = penggunaRepository.findOneByUsername(username);

      tulisan.setJudul(stripTags(tulisan.getJudul()));
      tulisan.setSlug(lowerCase((pregReplace(tulisan.getJudul()))).replace(" ", "-"));
      tulisan.setPengguna(pengguna);
      tulisan.setCreatedBy(username);
      tulisan.setGambar(encoded);
      tulisan.setPhotoBase64(encoded);
      tulisan.setFileContent(bytes);
      tulisan.setFilename(file.getOriginalFilename());
      tulisanRepository.save(tulisan);
      status.setComplete();
      log.info("Tambah Tulisan yang baru sukses.");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @GetMapping("/delete/{tulisanId}")
  public String deleteTulisan(@PathVariable("tulisanId") UUID tulisanId, Model model, Pageable pageable) {
    log.info("Hapus data Tulisan");
    tulisanRepository.deleteById(tulisanId);

    model.addAttribute(KAT_LIST.getDescription(), kategoriRepository.findAll());
    model.addAttribute(TULISAN.getDescription(), tulisanRepository.findAll(pageable));

    return RIE_ADMIN_TUL.getDescription();
  }
}
