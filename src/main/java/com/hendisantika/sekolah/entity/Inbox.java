package com.hendisantika.sekolah.entity;

import java.util.UUID;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PositiveOrZero;
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
@Entity(name = "tbl_inbox")
@SQLDelete(sql = "UPDATE tbl_inbox SET status_record='INACTIVE' WHERE id=? AND version=?")
@SQLRestriction(value = "status_record='ACTIVE'")
public class Inbox extends AuditTableEntity<UUID> {
  @Column(name = "nama")
  @Size(max = 40)
  private String nama;

  @Column(name = "email")
  @Size(max = 60)
  @Email
  private String email;

  @Column(name = "kontak")
  @Size(max = 20)
  private String kontak;

  @Column(name = "pesan")
  private String pesan;

  @Column(name = "status")
  @PositiveOrZero
  private int status;
}
