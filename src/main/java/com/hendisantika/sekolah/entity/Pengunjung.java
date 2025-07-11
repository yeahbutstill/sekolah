package com.hendisantika.sekolah.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
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
@Builder
@Entity(name = "tbl_pengunjung")
@SQLDelete(sql = "UPDATE tbl_pengunjung SET status_record='INACTIVE' WHERE id=? AND version=?")
public class Pengunjung {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @Column(name = "session_id")
  @Size(max = 50)
  @NotNull
  private String sessionId;

  @Column(name = "cookie_name")
  @Size(max = 50)
  @NotNull
  private String cookieName;

  @Column(name = "url")
  @Size(max = 150)
  @NotNull
  private String url;

  @Column(name = "ip")
  @Size(max = 40)
  private String ipAddress;

  @Column(name = "os_type")
  @Size(max = 50)
  private String osType;

  @Column(name = "os_version")
  @Size(max = 50)
  private String osVersion;

  @Column(name = "browser_name")
  @Size(max = 50)
  private String browserName;

  @Column(name = "browser_type")
  @Size(max = 50)
  private String browserType;

  @Column(name = "browser_version")
  @Size(max = 50)
  private String browserVersion;

  @Column(name = "device_type")
  @Size(max = 50)
  private String deviceType;

  @Column(name = "host_address")
  @Size(max = 50)
  private String hostAddress;

  @Column(name = "host_name")
  @Size(max = 50)
  private String hostName;

  @Column(name = "created_on")
  private LocalDateTime createdOn;
}
