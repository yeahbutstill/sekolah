package com.hendisantika.sekolah.common.entity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

public class UpdatedCreatedAtListener {

    @PrePersist
    public void setLastCreatedOn(Object object) {
        if (object instanceof AuditTableEntity<?> entity) {
            // Hanya atur createdOn jika belum ada
            if (entity.getCreatedOn() == null) {
                entity.setCreatedOn(LocalDateTime.now());
            }
            // Opsional: Atur createdBy jika tidak dihandle oleh AuditingEntityListener
            if (entity.getCreatedBy() == null) {
                entity.setCreatedBy(getCurrentUser());
            }
        }
    }

    @PreUpdate
    public void setLastModifiedOn(Object object) {
        if (object instanceof AuditTableEntity<?> entity) {
            // Atur modifiedOn
            entity.setModifiedOn(LocalDateTime.now());
            // Atur modifiedBy
            entity.setModifiedBy(getCurrentUser());
        }
    }

    private String getCurrentUser() {
        // Ambil pengguna saat ini dari Spring Security atau mekanisme lain
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName(); // Mengembalikan nama pengguna
        }
        return "system"; // Default jika tidak ada pengguna yang terautentikasi
    }
}