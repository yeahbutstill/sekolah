package com.hendisantika.sekolah.entity;

import java.util.UUID;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
@Entity(name = "tbl_pengumuman")
@SQLDelete(sql = "UPDATE tbl_pengumuman SET status_record='INACTIVE' WHERE id=? AND version=?")
@SQLRestriction(value = "status_record='ACTIVE'")
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
