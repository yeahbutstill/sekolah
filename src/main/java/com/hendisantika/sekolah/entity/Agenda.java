package com.hendisantika.sekolah.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import com.hendisantika.sekolah.entity.base.AuditTableEntity;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Project : sekolah
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 17/03/20
 * Time: 13.59
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "tbl_agenda")
@SQLDelete(sql = "UPDATE tbl_agenda SET status_record='INACTIVE' WHERE id=? AND version=?")
public class Agenda extends AuditTableEntity<UUID> {
  @Column(name = "nama")
  @Size(max = 200)
  private String nama;

  @Column(name = "deskripsi")
  private String deskripsi;

  @Column(name = "mulai")
  private LocalDate mulai;

  @Column(name = "selesai")
  private LocalDate selesai;

  @Column(name = "tempat")
  @Size(max = 90)
  private String tempat;

  @Column(name = "waktu")
  @Size(max = 30)
  private String waktu;

  @Column(name = "keterangan")
  @Size(max = 200)
  private String keterangan;
}
