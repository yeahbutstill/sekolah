package com.hendisantika.sekolah.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.hendisantika.sekolah.entity.Album;
import com.hendisantika.sekolah.repository.base.CustomJpaRepository;

/**
 * Created by IntelliJ IDEA.
 * Project : sekolah
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 03/04/20
 * Time: 07.53
 */
@Repository
public interface AlbumRepository extends CustomJpaRepository<Album, UUID> {
}
