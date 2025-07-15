package com.hendisantika.sekolah.service.impl;

import com.hendisantika.sekolah.dto.PenggunaDto;
import com.hendisantika.sekolah.entity.Pengguna;
import com.hendisantika.sekolah.repository.PenggunaRepository;
import com.hendisantika.sekolah.service.PenggunaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;
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
@Service
@RequiredArgsConstructor
public class PenggunaServiceImpl implements PenggunaService {
    private final PenggunaRepository penggunaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true) // Hanya membaca, tidak mengubah data
    public Page<Pengguna> findAllPengguna(Pageable pageable) {
        log.info("Mengambil semua data Pengguna dari database.");
        return penggunaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pengguna> findPenggunaById(UUID id) {
        log.info("Mengambil data Pengguna dengan ID: {}", id);
        return penggunaRepository.findById(id);
    }

    @Override
    @Transactional // Melakukan perubahan data
    public Pengguna savePengguna(PenggunaDto penggunaDto, MultipartFile file, Principal principal) throws IOException {
        log.info("Menyimpan Pengguna baru: {}", penggunaDto.getUsername());
        Pengguna pengguna = new Pengguna();
        BeanUtils.copyProperties(penggunaDto, pengguna);

        // Encoding password
        pengguna.setPassword(passwordEncoder.encode(penggunaDto.getPassword()));

        // Handle file (photo/file content)
        if (file != null && !file.isEmpty()) {
            byte[] bytes = file.getBytes();
            String encoded = Base64.getEncoder().encodeToString(bytes);
            pengguna.setPhoto(file.getOriginalFilename());
            pengguna.setPhotoBase64(encoded);
            pengguna.setFileContent(bytes);
            pengguna.setFilename(file.getOriginalFilename());
        }

        // Set createdBy if Principal is available (though AuditorAware handles this)
        // If AuditorAware is correctly configured, you don't need this explicit setting.
        // if (principal != null) {
        //     pengguna.setCreatedBy(principal.getName());
        // }

        Pengguna savedPengguna = penggunaRepository.save(pengguna);
        log.info("Pengguna baru berhasil disimpan dengan ID: {}", savedPengguna.getId());
        return savedPengguna;
    }

    @Override
    @Transactional // Melakukan perubahan data
    public Pengguna updatePengguna(UUID id, PenggunaDto penggunaDto, MultipartFile file, Principal principal) throws IOException {
        log.info("Memperbaharui data Pengguna dengan ID: {}", id);
        Optional<Pengguna> currPenggunaOptional = penggunaRepository.findById(id);

        if (currPenggunaOptional.isEmpty()) {
            log.warn("Pengguna dengan ID: {} tidak ditemukan untuk update.", id);
            // Anda bisa melempar exception kustom di sini
            throw new IllegalArgumentException("Pengguna tidak ditemukan untuk ID: " + id);
        }

        Pengguna pengguna = currPenggunaOptional.get();

        // Update fields from DTO
        BeanUtils.copyProperties(penggunaDto, pengguna, "id", "password", "photo", "photoBase64", "fileContent", "filename"); // Hindari meng-copy ID dan password/file langsung dari DTO

        // Only update password if DTO provides a new one and it's different/not empty
        if (penggunaDto.getPassword() != null && !penggunaDto.getPassword().isEmpty()) {
            pengguna.setPassword(passwordEncoder.encode(penggunaDto.getPassword()));
        }

        // Handle file update
        if (file != null && !file.isEmpty()) {
            byte[] bytes = file.getBytes();
            String encoded = Base64.getEncoder().encodeToString(bytes);
            pengguna.setPhoto(file.getOriginalFilename());
            pengguna.setPhotoBase64(encoded);
            pengguna.setFileContent(bytes);
            pengguna.setFilename(file.getOriginalFilename());
        }

        // Set modifiedBy if Principal is available (though AuditorAware handles this)
        // If AuditorAware is correctly configured, you don't need this explicit setting.
        // if (principal != null) {
        //     pengguna.setModifiedBy(principal.getName());
        // }

        Pengguna updatedPengguna = penggunaRepository.save(pengguna);
        log.info("Data Pengguna dengan ID: {} berhasil diperbaharui.", updatedPengguna.getId());
        return updatedPengguna;
    }

    @Override
    @Transactional // Melakukan perubahan data (soft delete)
    public void deletePenggunaById(UUID id) {
        log.info("Memulai penghapusan (soft delete) Pengguna dengan ID: {}", id);
        // Spring Data JPA deleteById akan memicu @SQLDelete jika entitas dikonfigurasi.
        penggunaRepository.deleteById(id);
        log.info("Pengguna dengan ID: {} berhasil di-soft delete.", id);
    }

//    public UserDetails loadUserByUsername(String username) {
//        Pengguna user = penggunaRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException(username);
//        }
//        return new MyUserPrincipal(user);
//    }
}
