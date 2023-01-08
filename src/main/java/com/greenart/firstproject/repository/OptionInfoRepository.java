package com.greenart.firstproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.firstproject.entity.OptionInfoEntity;
import com.greenart.firstproject.entity.ProductInfoEntity;

@Repository
public interface OptionInfoRepository extends JpaRepository<OptionInfoEntity, Long>{
    List<OptionInfoEntity> findByOptionAndPrice(String option, Integer price);

    List<OptionInfoEntity> findByProduct(ProductInfoEntity product);
}
