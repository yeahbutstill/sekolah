package com.hendisantika.sekolah.repository;

import com.hendisantika.sekolah.entity.Pengguna;
import com.hendisantika.sekolah.repository.base.CustomJpaRepository;

import org.springframework.stereotype.Repository;

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
public interface PenggunaRepository extends CustomJpaRepository<Pengguna, UUID> {
}
