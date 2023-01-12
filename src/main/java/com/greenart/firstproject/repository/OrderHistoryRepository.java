package com.greenart.firstproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.firstproject.entity.OrderHistoryEntity;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistoryEntity, Long> {
    
}