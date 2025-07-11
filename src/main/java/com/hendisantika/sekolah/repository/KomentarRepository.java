package com.hendisantika.sekolah.repository;

import com.hendisantika.sekolah.entity.Komentar;
import com.hendisantika.sekolah.repository.base.CustomJpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
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
public interface KomentarRepository extends CustomJpaRepository<Komentar, UUID> {
  List<Komentar> findByTulisanIdAndStatusAndParent(UUID slug, String status, int parent);

  List<Komentar> findByStatusAndParentOrderByCreatedOnAsc(String status, int parent);
}
