package com.hendisantika.sekolah.repository.base;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import com.hendisantika.sekolah.repository.base.impl.CustomJpaRepositoryImpl;

import jakarta.persistence.EntityManager;

/**
 * Factory bean untuk membuat instance CustomJpaRepository.
 * Ini memungkinkan kita untuk mengintegrasikan implements repository kustom
 * ke dalam ekosistem Spring Data JPA.
 *
 * @param <R>  Type repository yang diperluas.
 * @param <T>  Type entitas yang dikelola oleh repository.
 * @param <ID> Type ID entitas.
 */
public class CustomJpaRepositoryFactoryBean<R extends JpaRepository<T, ID>, T, ID extends Serializable>
    extends JpaRepositoryFactoryBean<R, T, ID> {

  public CustomJpaRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
    super(repositoryInterface);
  }

  /**
   * Membuat dan mengembalikan instance RepositoryFactorySupport kustom.
   * Ini adalah titik di mana kita menyuntikkan pabrik repository kustom kita.
   *
   * @param em EntityManager yang akan digunakan oleh pabrik repository.
   * @return Instance RepositoryFactorySupport kustom.
   */
  @SuppressWarnings("rawtypes")
  @Override
  protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
    return new EntityRepositoryFactory(em);
  }

  /**
   * Pabrik repository kustom yang bertanggung jawab untuk membuat instance
   * CustomJpaRepositoryImpl.
   *
   * @param <T>  Type entitas yang dikelola oleh repository.
   * @param <ID> Type ID entitas.
   */
  private static class EntityRepositoryFactory<T, ID extends Serializable> extends JpaRepositoryFactory {
    public EntityRepositoryFactory(EntityManager entityManager) {
      super(entityManager);
    }

    /**
     * Mengembalikan instance implements repository target.
     * Di sini kita memastikan bahwa CustomJpaRepositoryImpl kita digunakan.
     *
     * @param information   Informasi tentang repository yang akan dibuat.
     * @param entityManager EntityManager yang akan digunakan oleh repository.
     * @return Instance JpaRepositoryImplementation kustom.
     */
    @Override
    @SuppressWarnings("unchecked")
    protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information,
        EntityManager entityManager) {
      JpaEntityInformation<T, ?> entityInformation = (JpaEntityInformation<T, ?>) getEntityInformation(
          information.getDomainType());
      return new CustomJpaRepositoryImpl<T, ID>(entityInformation, entityManager);
    }

    /**
     * Mengembalikan kelas dasar untuk repository.
     * Ini memberi tahu Spring Data JPA bahwa CustomJpaRepositoryImpl adalah
     * implements dasar untuk antarmuka repository kustom kita.
     *
     * @param metadata Metadata tentang repository.
     * @return Kelas dasar repository.
     */
    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
      return CustomJpaRepositoryImpl.class;
    }
  }
}
