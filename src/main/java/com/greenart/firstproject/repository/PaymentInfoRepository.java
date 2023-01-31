package com.greenart.firstproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greenart.firstproject.entity.PaymentInfoEntity;
import com.greenart.firstproject.vo.cart.PaymentInfoVO;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfoEntity, Long> {
    @Query
    (
        value = "select pay from PaymentInfoEntity pay join pay.user u join fetch pay.orderhistories oh join fetch oh.orderhistories where u.seq = :seq"
        )
    List<PaymentInfoEntity> findByUserSeqWithFetch(@Param("seq") Long userSeq);
    }

// @Query(value = "select c from CartInfoEntity c join c.user u join fetch c.option o join fetch o.product where u.seq = :seq")
// List<CartInfoEntity> findByUserSeqWithFetch(@Param("seq") Long userSeq);
    
// @Query(value = "select oh from OrderHistoryEntity oh join oh.user u join fetch oh.product c join fetch c.cart where u.seq = :seq")
// List<OrderHistoryEntity> findByUserSeqWithFetch(@Param("seq") Long userSeq);
