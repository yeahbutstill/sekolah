package com.hendisantika.sekolah.repository;

import com.hendisantika.sekolah.entity.Kategori;
import com.hendisantika.sekolah.repository.base.CustomJpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Project : sekolah
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 18/03/20
 * Time: 14.23
 */
@Repository
public interface KategoriRepository extends CustomJpaRepository<Kategori, UUID> {
  Optional<Kategori> findById(UUID kategoriId);
}
