package com.hendisantika.sekolah.controller;

import static com.hendisantika.sekolah.enumeration.ALLCONSTANT.PENGGUNA;
import static com.hendisantika.sekolah.enumeration.ALLCONSTANT.RIE_ADMIN_PENGGUNA;

import java.io.IOException;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.hendisantika.sekolah.dto.PenggunaDto;
import com.hendisantika.sekolah.service.PenggunaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by IntelliJ IDEA.
 * Project : sekolah
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 29/03/20
 * Time: 13.13
 */

@Slf4j
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@Controller
@RequestMapping("/admin/pengguna")
public class PenggunaController {

  private final PenggunaService penggunaService; // Inject the service instead of repository and passwordEncoder

  @GetMapping
  public String pengguna(Model model, Pageable pageable) {
    log.info("Displaying data for User List Page.");
    model.addAttribute("penggunaList", penggunaService.getAllPengguna(pageable));
    return "/admin/pengguna/pengguna-list";
  }

  @GetMapping("/add")
  public String showFormPengguna(Model model) {
    log.info("Displaying Add User Form.");
    model.addAttribute(PENGGUNA.getDescription(), new PenggunaDto());
    return "/admin/pengguna/pengguna-form";
  }

  @GetMapping("/edit/{penggunaId}")
  public String showEditPenggunaForm(@PathVariable("penggunaId") UUID penggunaId, Model model) {
    log.info("Displaying Edit User Form for ID: {}", penggunaId);
    model.addAttribute(PENGGUNA.getDescription(), penggunaService.getPenggunaById(penggunaId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
    return "/admin/pengguna/pengguna-edit";
  }

  @PostMapping("/edit")
  public String editPengguna(Model model, @Valid PenggunaDto penggunaDto,
      @RequestParam(value = "file", required = false) MultipartFile file,
      Pageable pageable, SessionStatus status) {
    log.info("Updating User data.");
    try {
      // Ensure ID is present for update
      if (penggunaDto.getId() == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID is required for update.");
      }
      penggunaService.updatePengguna(penggunaDto.getId(), penggunaDto, file);
      status.setComplete();
      log.info("User data update successful.");
    } catch (IOException e) {
      log.error("Error updating user data: {}", e.getMessage(), e);
      // Add error message to model if needed
      model.addAttribute("errorMessage", "Failed to update user: " + e.getMessage());
      return "/admin/pengguna/pengguna-edit"; // Return to form with error
    } catch (ResponseStatusException e) {
      log.error("Error during user update: {}", e.getReason());
      model.addAttribute("errorMessage", e.getReason());
      return "/admin/pengguna/pengguna-edit"; // Return to form with error
    }
    model.addAttribute("penggunaList", penggunaService.getAllPengguna(pageable));
    return RIE_ADMIN_PENGGUNA.getDescription();
  }

  @GetMapping("/delete/{penggunaId}")
  public String deletePengguna(@PathVariable("penggunaId") UUID penggunaId, Model model, Pageable pageable) {
    log.info("Deleting User Data with ID: {}", penggunaId);
    penggunaService.deletePengguna(penggunaId);
    model.addAttribute("penggunaList", penggunaService.getAllPengguna(pageable));
    return RIE_ADMIN_PENGGUNA.getDescription();
  }

  @PostMapping
  public String addPengguna(Model model, @Valid PenggunaDto penggunaDto, @RequestParam("file") MultipartFile file,
      Pageable pageable, SessionStatus status) {
    log.info("Preparing data for Add New User.");
    try {
      penggunaService.addPengguna(penggunaDto, file);
      status.setComplete();
      log.info("New user added successfully.");
    } catch (IOException e) {
      log.error("Error adding new user: {}", e.getMessage(), e);
      // Add error message to model if needed
      model.addAttribute("errorMessage", "Failed to add user: " + e.getMessage());
      return "/admin/pengguna/pengguna-form"; // Return to form with error
    }
    model.addAttribute("penggunaList", penggunaService.getAllPengguna(pageable));
    return RIE_ADMIN_PENGGUNA.getDescription();
  }
}
