package com.hendisantika.sekolah.repository.base;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Antarmuka repository kustom yang menyediakan metode tambahan di luar
 * yang disediakan oleh JpaRepository standard.
 * Anotasi @NoRepositoryBean mencegah Spring Data JPA membuat implements
 * untuk antarmuka ini secara langsung.
 *
 * @param <T>  Type entitas.
 * @param <ID> Type ID entitas.
 */
@NoRepositoryBean
public interface CustomJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
  /**
   * Melepaskan entitas dari konteks persistensi.
   *
   * @param oldRecord Entitas yang akan dilepas.
   */
  void detach(T oldRecord);

  /**
   * Mengambil satu entitas berdasarkan kunci utamanya.
   *
   * @param id Kunci utama yang akan dicari.
   * @return Entitas, tidak pernah Null.
   * @throws jakarta.persistence.EntityNotFoundException ketika ID tidak ditemukan
   *                                                     di DB.
   */
  T findOneById(ID id);

  /**
   * Mengambil satu entitas berdasarkan nama pengguna.
   * Melemparkan EntityNotFoundException jika entitas tidak ditemukan.
   *
   * @param username Nama pengguna yang akan dicari.
   * @return Entitas, tidak pernah null.
   * @throws jakarta.persistence.EntityNotFoundException ketika entitas dengan
   *                                                     nama pengguna yang
   *                                                     diberikan tidak ditemukan
   *                                                     di database.
   */
  T findOneByUsername(String username); // Mengubah type kembalian dari Optional<T> menjadi T
}
