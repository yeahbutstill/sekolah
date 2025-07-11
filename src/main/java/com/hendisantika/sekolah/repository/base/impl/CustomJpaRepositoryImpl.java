package com.hendisantika.sekolah.repository.base.impl;

import java.io.Serializable;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.hendisantika.sekolah.repository.base.CustomJpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;

/**
 * Implements kustom dari CustomJpaRepository.
 * Menyediakan implements untuk metode tambahan yang didefinisikan dalam
 * CustomJpaRepository.
 *
 * @param <T>  Type entitas.
 * @param <ID> Type ID entitas.
 */
public class CustomJpaRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
    implements CustomJpaRepository<T, ID> {

  private final EntityManager entityManager;

  /**
   * Konstruktor untuk CustomJpaRepositoryImpl.
   *
   * @param entityInformation Informasi entitas JPA.
   * @param entityManager     EntityManager yang akan digunakan.
   */
  public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
    super(entityInformation, entityManager);
    this.entityManager = entityManager;
  }

  /**
   * Melepaskan (detach) entitas yang diberikan dari konteks persistensi.
   * Setiap perubahan yang dilakukan pada entitas yang dilepas tidak akan
   * disinkronkan dengan database kecuali jika di-attach ulang (misalnya, dengan
   * memanggil merge).
   * Metode ini memerlukan transaksi aktif.
   *
   * @param entity Entitas yang akan dilepas.
   */
  @Override
  @Transactional
  public void detach(T entity) {
    entityManager.detach(entity);
  }

  /**
   * Mengambil satu entitas berdasarkan kunci utamanya.
   * Implements ini secara eksplisit melempar EntityNotFoundException jika
   * entitas tidak ditemukan.
   * Ini menggunakan findById() dari JpaRepository yang mengembalikan Optional,
   * lalu menggunakan orElseThrow.
   *
   * @param id Kunci utama yang akan dicari.
   * @return Entitas, tidak pernah null.
   * @throws EntityNotFoundException Ketika entitas dengan ID yang diberikan tidak
   *                                 ditemukan di database.
   */
  @Override
  @Transactional(readOnly = true)
  public T findOneById(ID id) {
    return findById(id)
        .orElseThrow(() -> new EntityNotFoundException("No " + getDomainClass().getSimpleName() + " with id " + id));
  }

  /**
   * Mengambil satu entitas berdasarkan nama pengguna.
   * Menggunakan JPQL untuk melakukan pencarian.
   * Melemparkan EntityNotFoundException jika entitas tidak ditemukan.
   *
   * @param username Nama pengguna yang akan dicari.
   * @return Entitas, tidak pernah null.
   * @throws EntityNotFoundException Ketika entitas dengan nama pengguna yang
   *                                 diberikan tidak ditemukan di database.
   */
  @Override
  @Transactional(readOnly = true) // Tambahkan anotasi transaksi read-only
  public T findOneByUsername(String username) {
    try {
      return entityManager
          .createQuery("SELECT e FROM " + getDomainClass().getName() + " e WHERE e.username = :username",
              getDomainClass())
          .setParameter("username", username)
          .getSingleResult();
    } catch (NoResultException e) {
      // Jika tidak ada hasil, lemparkan EntityNotFoundException
      throw new EntityNotFoundException("No " + getDomainClass().getSimpleName() + " with username " + username);
    }
  }
}
