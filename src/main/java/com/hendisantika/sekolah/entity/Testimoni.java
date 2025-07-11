package com.hendisantika.sekolah.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import com.hendisantika.sekolah.entity.base.AuditTableEntity;

import java.util.UUID;

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
@Entity(name = "tbl_testimoni")
@SQLDelete(sql = "UPDATE tbl_testimoni SET status_record='INACTIVE' WHERE id=? AND version=?")
public class Testimoni extends AuditTableEntity<UUID> {
  @Column(name = "nama")
  @Size(max = 30)
  private String nama;

  @Column(name = "isi")
  @Size(max = 120)
  private String isi;

  @Column(name = "email")
  @Email
  @Size(max = 35)
  private String email;
}
