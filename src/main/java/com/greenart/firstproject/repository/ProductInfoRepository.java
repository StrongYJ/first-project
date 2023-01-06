package com.greenart.firstproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.firstproject.entity.ProductInfoEntity;

@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfoEntity, Long>{
    public Page<ProductInfoEntity> findAll(Pageable pageable);
    public List<ProductInfoEntity> findByType(String type, Pageable pageable);
    List<ProductInfoEntity> findByName(String name);
}
