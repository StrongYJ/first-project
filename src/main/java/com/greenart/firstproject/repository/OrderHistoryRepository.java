package com.greenart.firstproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greenart.firstproject.entity.OrderHistoryEntity;
import com.greenart.firstproject.entity.UserEntity;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistoryEntity, Long> {
    public OrderHistoryEntity findByUser(UserEntity user);
    
}
