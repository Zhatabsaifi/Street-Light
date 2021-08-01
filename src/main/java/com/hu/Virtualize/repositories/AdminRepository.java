package com.hu.Virtualize.repositories;

import com.hu.Virtualize.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    Optional<AdminEntity> findByAdminEmail(String adminEmail);
    AdminEntity findByAdminId(Long adminId);
}
