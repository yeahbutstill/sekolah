package com.hendisantika.sekolah.controller;

import static com.hendisantika.sekolah.enumeration.ALLCONSTANT.PENGUMUMAN;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.hendisantika.sekolah.dto.PengumumanDto;
import com.hendisantika.sekolah.entity.Pengguna;
import com.hendisantika.sekolah.entity.Pengumuman;
import com.hendisantika.sekolah.repository.PenggunaRepository;
import com.hendisantika.sekolah.repository.PengumumanRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by IntelliJ IDEA.
 * Project : sekolah
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 28/03/20
 * Time: 10.53
 */
@Slf4j
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@Controller
@RequestMapping("/admin/pengumuman")
public class PengumumanController {
  private final PengumumanRepository pengumumanRepository;
  private final PenggunaRepository penggunaRepository;

  /**
   * Menampilkan daftar pengumuman.
   *
   * @param model    Model untuk menambahkan attribute.
   * @param pageable Objek Pageable untuk paginasi.
   * @return Nama tampilan untuk daftar pengumuman.
   */
  @GetMapping
  public String showPengumuman(Model model, Pageable pageable) {
    log.info("Menampilkan data untuk Halaman List Pengumuman.");
    model.addAttribute("pengumumanList", pengumumanRepository.findAll(pageable));
    return "/admin/pengumuman/pengumuman-list";
  }

  /**
   * Menampilkan formulir untuk menambah pengumuman baru.
   *
   * @param model Model untuk menambahkan attribute.
   * @return Nama tampilan untuk formulir pengumuman.
   */
  @GetMapping("/add")
  public String showFormPengumuman(Model model) {
    log.info("Menampilkan Form untuk Tambah Pengumuman.");
    // Asumsi PENGUMUMAN.getDescription() mengembalikan string yang tepat, misal
    // "pengumuman"
    model.addAttribute(PENGUMUMAN.getDescription(), new Pengumuman());
    return "/admin/pengumuman/pengumuman-form";
  }

  /**
   * Menampilkan formulir untuk mengedit pengumuman berdasarkan ID.
   *
   * @param pengumumanId ID pengumuman yang akan diedit.
   * @param model        Model untuk menambahkan attribute.
   * @return Nama tampilan untuk formulir edit pengumuman.
   */
  @GetMapping("/edit/{pengumumanId}")
  public String showFormPengumuman(@PathVariable("pengumumanId") UUID pengumumanId, Model model) {
    log.info("Menampilkan Form untuk Edit Pengumuman by id: {}", pengumumanId);
    // Menggunakan orElse(null) atau orElseThrow jika ingin menangani kasus tidak
    // ditemukan
    // Untuk tujuan edit, sebaiknya dilempar NotFoundException jika tidak ada
    Pengumuman pengumuman = pengumumanRepository.findOneById(pengumumanId);
    model.addAttribute(PENGUMUMAN.getDescription(), pengumuman);
    return "/admin/pengumuman/pengumuman-edit";
  }

  /**
   * Menghapus pengumuman berdasarkan ID.
   *
   * @param pengumumanId ID pengumuman yang akan dihapus.
   * @param model        Model untuk menambahkan attribute.
   * @param pageable     Objek Pageable untuk paginasi.
   * @return Redirect ke daftar pengumuman setelah penghapusan.
   */
  @GetMapping("/delete/{pengumumanId}")
  public String deletePengumuman(@PathVariable("pengumumanId") UUID pengumumanId, Model model, Pageable pageable) {
    log.info("Delete Pengumuman by id: {}", pengumumanId);
    pengumumanRepository.deleteById(pengumumanId);
    // Setelah delete, mungkin tidak perlu menambahkan kembali semua data ke model
    // Cukup redirect agar data terbaru diambil dari database
    return "redirect:/admin/pengumuman";
  }

  /**
   * Memperbarui data pengumuman.
   *
   * @param pengumumanDto Objek PengumumanDto yang berisi data yang diperbarui.
   * @param model         Model untuk menambahkan attribute.
   * @param pageable      Objek Pageable untuk paginasi.
   * @return Redirect ke daftar pengumuman setelah pembaruan, atau string
   *         kesalahan jika gagal.
   */
  @PostMapping("/edit")
  public String updatePengumuman(@Valid PengumumanDto pengumumanDto, Model model, Pageable pageable) {
    log.info("Memperbaharui data Pengumuman by id: {}", pengumumanDto.getId());
    Optional<Pengumuman> byId = pengumumanRepository.findById(pengumumanDto.getId());
    if (byId.isPresent()) {
      Pengumuman pengumuman = byId.get();
      pengumuman.setJudul(pengumumanDto.getJudul());
      pengumuman.setDeskripsi(pengumumanDto.getDeskripsi());
      pengumumanRepository.save(pengumuman);
      // Setelah update, cukup redirect
      return "redirect:/admin/pengumuman";
    } else {
      // Lebih baik mengembalikan ke halaman form dengan pesan error atau redirect ke
      // halaman error
      log.error("Error: Pengumuman with ID {} not found for update.", pengumumanDto.getId());
      // Contoh: return "redirect:/error?message=Pengumuman not found";
      // Atau, jika Anda ingin menampilkan pesan kesalahan di halaman yang sama:
      model.addAttribute("errorMessage", "Pengumuman tidak ditemukan untuk diperbarui.");
      return "/admin/pengumuman/pengumuman-edit"; // Kembali ke halaman edit dengan pesan error
    }
  }

  /**
   * Menambahkan pengumuman baru.
   *
   * @param model      Model untuk menambahkan attribute.
   * @param pengumuman Objek Pengumuman yang akan ditambahkan.
   * @param principal  Objek Principal yang berisi informasi pengguna yang sedang
   *                   login.
   * @param pageable   Objek Pageable untuk paginasi.
   * @param status     Objek SessionStatus untuk menyelesaikan sesi.
   * @return Nama tampilan untuk daftar pengumuman setelah penambahan.
   */
  @PostMapping
  public String addPengumuman(Model model, @Valid Pengumuman pengumuman,
      Principal principal, Pageable pageable, SessionStatus status) {
    log.info("Menambahkan data Pengumuman: {}", pengumuman);
    String username = principal.getName();

    Pengguna pengguna = penggunaRepository.findOneByUsername(username);

    pengumuman.setAuthor(pengguna.getFullname());
    pengumumanRepository.save(pengumuman);
    status.setComplete(); // Membersihkan attribute sesi yang ditandai untuk sesi lengkap
    // Setelah penambahan, cukup redirect
    return "redirect:/admin/pengumuman";
  }
}
