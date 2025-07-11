package com.hendisantika.sekolah.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
@Entity(name = "tbl_galeri")
@SQLDelete(sql = "UPDATE tbl_galeri SET status_record='INACTIVE' WHERE id=? AND version=?")
public class Galeri extends AuditTableEntity<UUID> {
  @ManyToOne
  @JoinColumn(name = "album_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @ToString.Exclude
  @NotNull
  private Album album;

  @ManyToOne
  @JoinColumn(name = "pengguna_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @ToString.Exclude
  @NotNull
  private Pengguna pengguna;

  @Column(name = "judul")
  @Size(max = 60)
  private String judul;

  @Column(name = "author")
  @Size(max = 60)
  private String author;

  @Column(name = "gambar")
  @Size(max = 40)
  private String gambar;

  @Column(name = "photo_base64")
  private String photoBase64;

  @Column(name = "filename")
  @Size(max = 50)
  private String filename;

  @Column(name = "file_content")
  private byte[] fileContent;
}
