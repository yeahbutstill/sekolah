package com.hendisantika.sekolah.entity;

import com.hendisantika.sekolah.common.entity.AuditTableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
@Entity(name = "tbl_pengguna")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SQLDelete(sql = "UPDATE tbl_pengguna SET status_record='INACTIVE' WHERE id=? AND version=?")
@SQLRestriction(value = "status_record='ACTIVE'")
public class Pengguna extends AuditTableEntity<UUID> {
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinTable(name = "tbl_pengguna_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @Column(name = "fullname")
    @Size(max = 50)
    @NotBlank
    @NotEmpty
    private String fullname;

    @Column(name = "moto")
    @Size(max = 120)
    private String moto;

    @Column(name = "jenkel")
    @Size(max = 1)
    @NotBlank
    @NotEmpty
    private String jenkel;

    @Column(name = "username", unique = true)
    @Size(max = 30)
    @NotBlank
    @NotEmpty
    private String username;

    @Column(name = "password")
    @Size(max = 75)
    @NotBlank
    @NotEmpty
    private String password;

    @Column(name = "tentang")
    private String tentang;

    @Column(name = "email", unique = true)
    @Email
    @Size(max = 50)
    @NotBlank
    @NotEmpty
    private String email;

    @Column(name = "nohp")
    @Size(max = 20)
    @NotBlank
    @NotEmpty
    private String nohp;

    @Column(name = "facebook")
    @Size(max = 50)
    private String facebook;

    @Column(name = "twitter")
    @Size(max = 50)
    private String twitter;

    @Column(name = "linkedin")
    @Size(max = 50)
    private String linkedin;

    @Column(name = "photo")
    @Size(max = 40)
    private String photo;

    @Column(name = "photo_base64")
    private String photoBase64;

    @Column(name = "filename")
    @Size(max = 40)
    private String filename;

    @Column(name = "file_content")
    private byte[] fileContent;
}
