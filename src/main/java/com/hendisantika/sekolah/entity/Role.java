package com.hendisantika.sekolah.entity;

import com.hendisantika.sekolah.common.entity.AuditTableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;
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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@SQLDelete(sql = "UPDATE role SET status_record='INACTIVE' WHERE id=? AND version=?")
@SQLRestriction(value = "status_record='ACTIVE'")
public class Role extends AuditTableEntity<UUID> {
    private String role;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Pengguna> users = new ArrayList<>();

}
