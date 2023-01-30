package com.greenart.firstproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenart.firstproject.entity.PaymentInfoEntity;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfoEntity, Long> {
    
}
