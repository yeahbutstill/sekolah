package com.hendisantika.sekolah.common.entity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class UpdatedCreatedAtListener {

    @PreUpdate
    public void setLastModifiedOn(Object object) {
        // Pastikan objek adalah turunan dari AuditTableEntity
        if (object instanceof AuditTableEntity<?> entity) {
            entity.setModifiedOn(LocalDateTime.now());
        }
    }

    @PrePersist
    public void setLastCreatedOn(Object object) {
        // Pastikan objek adalah turunan dari AuditTableEntity
        if (object instanceof AuditTableEntity<?> entity) {
            // Hanya atur createdOn jika belum ada (misalnya, jika dibuat secara manual di test)
            if (entity.getCreatedOn() == null) {
                entity.setCreatedOn(LocalDateTime.now());
            }
        }
    }
}
