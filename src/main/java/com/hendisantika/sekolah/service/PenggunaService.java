package com.hendisantika.sekolah.service;

import com.hendisantika.sekolah.dto.PenggunaDto;
import com.hendisantika.sekolah.entity.Pengguna;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public interface PenggunaService {
  Pengguna saveUser(Pengguna user);

  List<Pengguna> getUsers();

  Page<Pengguna> getAllPengguna(Pageable pageable);

  Optional<Pengguna> getPenggunaById(UUID id);

  Pengguna addPengguna(PenggunaDto penggunaDto, MultipartFile file) throws IOException;

  Optional<Pengguna> updatePengguna(UUID id, PenggunaDto penggunaDto, MultipartFile file)
      throws IOException;

  void deletePengguna(UUID id);
}
