package com.hendisantika.sekolah.repository;

import com.hendisantika.sekolah.entity.Pengguna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PenggunaRepository extends JpaRepository<Pengguna, UUID> {
    //    @Query("FROM Pengguna WHERE username LIKE '%' || ?1 || '%'")
    Optional<Pengguna> findByUsername(String username);

    Optional<Pengguna> findByEmail(String mail);
}
