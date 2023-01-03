package com.greenart.firstproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.firstproject.entity.ProductInfoEntity;

@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfoEntity, Long>{
    
}
