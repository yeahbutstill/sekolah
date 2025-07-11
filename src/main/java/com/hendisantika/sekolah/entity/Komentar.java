package com.hendisantika.sekolah.entity;

import java.util.UUID;

import org.hibernate.annotations.SQLDelete;

import com.hendisantika.sekolah.entity.base.AuditTableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PositiveOrZero;
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
@Entity(name = "tbl_komentar")
@SQLDelete(sql = "UPDATE tbl_komentar SET status_record='INACTIVE' WHERE id=? AND version=?")
public class Komentar extends AuditTableEntity<UUID> {
  @Column(name = "nama")
  @Size(max = 200)
  private String nama;

  @Column(name = "email")
  @Email
  @Size(max = 60)
  private String email;

  @Column(name = "isi")
  @Size(max = 120)
  private String isi;

  @Column(name = "status")
  @Size(max = 2)
  private String status;

  @Column(name = "tulisan_id")
  private UUID tulisanId;

  @Column(name = "parent")
  @PositiveOrZero
  private int parent;
}
