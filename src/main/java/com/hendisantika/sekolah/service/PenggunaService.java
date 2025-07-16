package com.hendisantika.sekolah.service;

import com.hendisantika.sekolah.dto.PenggunaDto;
import com.hendisantika.sekolah.entity.Pengguna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

public interface PenggunaService {
    Page<Pengguna> findAllPengguna(Pageable pageable);

    Optional<Pengguna> findPenggunaById(UUID id);

    Pengguna savePengguna(PenggunaDto penggunaDto, MultipartFile file, Principal principal) throws IOException;

    Pengguna updatePengguna(UUID id, PenggunaDto penggunaDto, MultipartFile file, Principal principal) throws IOException;

    void deletePenggunaById(UUID id);
}