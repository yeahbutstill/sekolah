package com.hendisantika.sekolah.common.entity;

import com.hendisantika.sekolah.enumeration.STATUSRECORD;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode // Pertimbangkan callSuper = true jika Anda ingin menyertakan field parent
@ToString
@EntityListeners({
        UpdatedCreatedAtListener.class // Ini listener kustom untuk @CreatedDate/@LastModifiedDate/@CreatedBy/@LastModifiedBy
})
// ini dimana T itu harus extends Serializable
public abstract class AuditTableEntity<T extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private T id;

    @Column(name = "created_by")
    @Size(max = 50)
    private String createdBy;

    // sudah dihandle @CreatedDate di kustom listener
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "modified_by")
    @Size(max = 50)
    private String modifiedBy;

    // sudah dihandle @LastModifiedDate di kustom listener
    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status_record")
    private STATUSRECORD statusRecord = STATUSRECORD.ACTIVE;

    @Version
    private Long version;

}
