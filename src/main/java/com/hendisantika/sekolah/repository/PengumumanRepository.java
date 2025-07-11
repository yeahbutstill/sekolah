package com.hendisantika.sekolah.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hendisantika.sekolah.entity.Pengumuman;
import com.hendisantika.sekolah.repository.base.CustomJpaRepository;

/**
 * Created by IntelliJ IDEA.
 * Project : sekolah
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 18/03/20
 * Time: 06.43
 */
@Repository
public interface PengumumanRepository extends CustomJpaRepository<Pengumuman, UUID> {
  @Query(value = "SELECT p.* FROM tbl_pengumuman p ORDER BY p.created_on DESC LIMIT 4;", nativeQuery = true)
  List<Pengumuman> findTop4();

  List<Pengumuman> findByOrderByCreatedOnDesc();
}
