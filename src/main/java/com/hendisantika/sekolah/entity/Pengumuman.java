package com.hendisantika.sekolah.entity;

import java.util.UUID;

import org.hibernate.annotations.SQLDelete;

import com.hendisantika.sekolah.entity.base.AuditTableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by IntelliJ IDEA.
 * Project : sekolah
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 17/03/20
 * Time: 15.19
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "tbl_pengumuman")
@SQLDelete(sql = "UPDATE tbl_pengumuman SET status_record='INACTIVE' WHERE id=? AND version=?")
public class Pengumuman extends AuditTableEntity<UUID> {
  @Column(name = "judul")
  @Size(max = 150)
  private String judul;

  @Column(name = "deskripsi")
  private String deskripsi;

  @Column(name = "author")
  @Size(max = 60)
  private String author;
}
