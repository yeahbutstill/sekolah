package com.hendisantika.sekolah.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.hendisantika.sekolah.entity.Files;
import com.hendisantika.sekolah.repository.base.CustomJpaRepository;

/**
 * Created by IntelliJ IDEA.
 * Project : sekolah
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 18/03/20
 * Time: 18.31
 */
@Repository
public interface FilesRepository extends CustomJpaRepository<Files, UUID> {
}
