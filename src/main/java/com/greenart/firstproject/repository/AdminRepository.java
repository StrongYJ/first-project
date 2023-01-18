package com.greenart.firstproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.firstproject.entity.AdminEntity;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    Long countByidAndPwd(String id, String pwd);
    Optional<AdminEntity> findByIdAndPwd(String id, String pwd);
}
