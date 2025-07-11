package com.hendisantika.sekolah.entity;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.SQLDelete;

import com.hendisantika.sekolah.entity.base.AuditTableEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
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
@Entity
@SQLDelete(sql = "UPDATE role SET status_record='INACTIVE' WHERE id=? AND version=?")
public class Role extends AuditTableEntity<UUID> {
  private String role;

  @ManyToMany(mappedBy = "roles")
  @ToString.Exclude
  private List<Pengguna> users;

}
