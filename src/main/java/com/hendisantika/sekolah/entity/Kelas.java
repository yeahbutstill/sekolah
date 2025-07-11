package com.hendisantika.sekolah.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.SQLDelete;

import com.hendisantika.sekolah.entity.base.AuditTableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
@Entity(name = "tbl_kelas")
@SQLDelete(sql = "UPDATE tbl_kelas SET status_record='INACTIVE' WHERE id=? AND version=?")
public class Kelas extends AuditTableEntity<UUID> {
  @OneToMany(mappedBy = "kelas")
  @NotNull
  @ToString.Exclude
  private Set<Siswa> siswa = new HashSet<>();

  @Column(name = "nama")
  @Size(max = 25)
  private String nama;
}
