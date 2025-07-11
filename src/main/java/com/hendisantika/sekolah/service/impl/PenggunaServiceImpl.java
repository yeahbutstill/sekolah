package com.hendisantika.sekolah.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hendisantika.sekolah.dto.PenggunaDto;
import com.hendisantika.sekolah.entity.Pengguna;
import com.hendisantika.sekolah.repository.PenggunaRepository;
import com.hendisantika.sekolah.service.PenggunaService;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Project : sekolah
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 20/03/20
 * Time: 05.57
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class PenggunaServiceImpl implements PenggunaService {

  private final PenggunaRepository penggunaRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Pengguna saveUser(Pengguna user) {
    return penggunaRepository.save(user);
  }

  @Override
  public List<Pengguna> getUsers() {
    return penggunaRepository.findAll();
  }

  @Override
  public Page<Pengguna> getAllPengguna(Pageable pageable) {
    log.info("Fetching all user data for page: {}", pageable.getPageNumber());
    return penggunaRepository.findAll(pageable);
  }

  @Override
  public Optional<Pengguna> getPenggunaById(UUID id) {
    log.info("Fetching user with ID: {}", id);
    return penggunaRepository.findById(id);
  }

  @Override
  public Pengguna addPengguna(PenggunaDto penggunaDto, MultipartFile file) throws IOException {
    log.info("Preparing to add new user: {}", penggunaDto.getUsername());

    // Process file
    byte[] bytes = file.getBytes();
    String encoded = Base64.getEncoder().encodeToString(bytes);

    Pengguna pengguna = new Pengguna();
    BeanUtils.copyProperties(penggunaDto, pengguna);
    pengguna.setPassword(passwordEncoder.encode(penggunaDto.getPassword()));
    pengguna.setPhoto(file.getOriginalFilename());
    pengguna.setPhotoBase64(encoded);
    pengguna.setFileContent(bytes);
    pengguna.setFilename(file.getOriginalFilename());

    Pengguna savedPengguna = penggunaRepository.save(pengguna);
    log.info("Successfully added new user: {}", savedPengguna.getUsername());
    return savedPengguna;
  }

  @Override
  public Optional<Pengguna> updatePengguna(UUID id, PenggunaDto penggunaDto, MultipartFile file) throws IOException {
    log.info("Preparing to update user with ID: {}", id);

    Optional<Pengguna> currPenggunaOptional = penggunaRepository.findById(id);

    if (currPenggunaOptional.isPresent()) {
      Pengguna pengguna = currPenggunaOptional.get();

      // Update basic properties from DTO
      // Consider what fields can actually be updated via the DTO besides password and
      // photo
      // For example, if username can be changed, add:
      // pengguna.setUsername(penggunaDto.getUsername());
      // It's generally good practice to explicitly map fields to update
      if (penggunaDto.getPassword() != null && !penggunaDto.getPassword().isEmpty()) {
        pengguna.setPassword(passwordEncoder.encode(penggunaDto.getPassword()));
      }

      // Handle file update only if a new file is provided
      if (file != null && !file.isEmpty()) {
        byte[] bytes = file.getBytes();
        String encoded = Base64.getEncoder().encodeToString(bytes);
        pengguna.setPhoto(file.getOriginalFilename());
        pengguna.setPhotoBase64(encoded);
        pengguna.setFileContent(bytes);
        pengguna.setFilename(file.getOriginalFilename());
      }

      Pengguna updatedPengguna = penggunaRepository.save(pengguna);
      log.info("Successfully updated user with ID: {}", updatedPengguna.getId());
      return Optional.of(updatedPengguna);
    } else {
      log.warn("User with ID {} not found for update.", id);
      return Optional.empty();
    }
  }

  @Override
  public void deletePengguna(UUID id) {
    log.info("Attempting to delete user with ID: {}", id);
    penggunaRepository.deleteById(id);
    log.info("Successfully deleted user with ID: {}", id);
  }
}
