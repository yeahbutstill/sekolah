package com.hendisantika.sekolah.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hendisantika.sekolah.entity.Agenda;
import com.hendisantika.sekolah.repository.base.CustomJpaRepository;

/**
 * Created by IntelliJ IDEA.
 * Project : sekolah
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 18/03/20
 * Time: 07.07
 */
@Repository
public interface AgendaRepository extends CustomJpaRepository<Agenda, UUID> {
  @Query(value = "SELECT a.* FROM tbl_agenda a ORDER BY a.created_on DESC LIMIT 4;", nativeQuery = true)
  List<Agenda> findTop4();
}
