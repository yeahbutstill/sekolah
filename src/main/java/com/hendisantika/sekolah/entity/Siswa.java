package com.hendisantika.sekolah.entity;

import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;

import com.hendisantika.sekolah.entity.base.AuditTableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
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
@Entity(name = "tbl_siswa")
@SQLDelete(sql = "UPDATE tbl_siswa SET status_record='INACTIVE' WHERE id=? AND version=?")
public class Siswa extends AuditTableEntity<UUID> {
  @ManyToOne
  @JoinColumn(name = "kelas_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @ToString.Exclude
  @NotNull
  private Kelas kelas;

  @Column(name = "nis", unique = true)
  @Size(max = 20)
  private String nis;

  @Column(name = "nama")
  @Size(max = 75)
  private String nama;

  @Column(name = "jenkel")
  @Size(max = 2)
  private String jenkel;

  @Column(name = "photo")
  @Size(max = 40)
  private String photo;

  @Column(name = "photo_base64")
  private String photoBase64;

  @Column(name = "filename")
  @Size(max = 50)
  private String filename;

  @Column(name = "file_content")
  private byte[] fileContent;
}
