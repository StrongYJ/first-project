package com.greenart.firstproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.firstproject.entity.CouponInfoEntity;

@Repository
public interface CouponInfoRefository extends JpaRepository<CouponInfoEntity, Long>{
    
}
