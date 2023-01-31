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
        value = "select pay from PaymentInfoEntity pay join pay.user u join fetch pay.option oh join fetch oh.orderhistories where u.seq = :seq"
        )
    List<PaymentInfoEntity> findByUserSeqWithFetch(@Param("seq") Long userSeq);
    }
    
    // value = "select new com.greenart.firstproject.vo.cart.PaymentInfoVO()
    // from OrderHistoryEntity oh left join PaymentInfoEntity pay on  pay.seq = oh.seq and pay.user.seq = :userInfoSeq"
    // List<PaymentInfoVO> getPaymentList(@Param("userInfoSeq") Long seq);
    
    // @Query(
    //     value = "select new com.greenart.firstproject.vo.localadmin.LocalMarketOptionStockVO(ms.seq, oi.product.name, oi.option, oi.price, ms.stock) 
    // from OptionInfoEntity oi left join MarketStockEntity ms on ms.option.seq = oi.seq and ms.market.seq = :marketInfoSeq"
    //     )
    // List<LocalMarketOptionStockVO> getOptionList(@Param("marketInfoSeq") Long marketInfoSeq);






// @Query(value = "select oh from OrderHistoryEntity oh join oh.user u join fetch oh.product c join fetch c.cart where u.seq = :seq")
// List<OrderHistoryEntity> findByUserSeqWithFetch(@Param("seq") Long userSeq);

// @Query("select u from User u where u.username = :name")
// List<User> methodName(@Param("name") String username);