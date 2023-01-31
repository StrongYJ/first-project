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
        value = "select pay.pay_seq, pay.pay_ui_seq, pay.pay_order_dt, oh.oh_oi_name, pay.pay_original, pay.pay_final, pay.pay_delivery_status, pay.pay_canceled from payment_info pay left join user_info u on pay.pay_ui_seq = u.ui_seq left join order_history oh on oh.oh_pay_seq = pay.pay_seq;"
        ,nativeQuery = true)
    List<PaymentInfoEntity> findByUserSeqWithFetch(@Param("seq") Long userSeq);
    }
    // @Query
    // (
    //     value = "select pay from PaymentInfoEntity pay join pay.user u join fetch pay.option oh join fetch oh.orderhistories where u.seq = :seq"
    //     )
    // List<PaymentInfoEntity> findByUserSeqWithFetch(@Param("seq") Long userSeq);
    // }
    
    // value = "select new com.greenart.firstproject.vo.cart.PaymentInfoVO()
    // from OrderHistoryEntity oh left join PaymentInfoEntity pay on  pay.seq = oh.seq and pay.user.seq = :userInfoSeq"
    // List<PaymentInfoVO> getPaymentList(@Param("userInfoSeq") Long seq);
    
// @Query(value = "select oh from OrderHistoryEntity oh join oh.user u join fetch oh.product c join fetch c.cart where u.seq = :seq")
// List<OrderHistoryEntity> findByUserSeqWithFetch(@Param("seq") Long userSeq);
