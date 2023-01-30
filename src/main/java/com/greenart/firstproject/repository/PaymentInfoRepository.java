package com.greenart.firstproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greenart.firstproject.entity.PaymentInfoEntity;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfoEntity, Long> {
    @Query
    (value = "select oh from PaymentInfoEntity oh join oh.user u join fetch oh.product where u.seq = :seq")
    List<PaymentInfoEntity> findByUserSeqWithFetch(@Param("seq") Long userSeq);
}
