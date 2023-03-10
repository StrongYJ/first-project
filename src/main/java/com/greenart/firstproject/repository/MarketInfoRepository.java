package com.greenart.firstproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.firstproject.entity.MarketInfoEntity;

@Repository
public interface MarketInfoRepository extends JpaRepository<MarketInfoEntity, Long> {
    List<MarketInfoEntity> findByAddress(String address);
    MarketInfoEntity findBySeq(Long seq);
}
