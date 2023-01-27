package com.greenart.firstproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greenart.firstproject.entity.OrderHistoryEntity;


@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistoryEntity, Long> {
    @Query
    // select oh.oh_seq , oh.oh_order_dt , oh.oh_oi_name , p.pi_name , c.ci_stock  , oh.oh_price ,oh.oh_canceled , oh.oh_delivery_status  from order_history oh
    // join user_info u on oh_ui_seq = u.ui_seq
    // join option_info o on oh_oi_name = o.oi_name
    // join product_info p on o.oi_pi_seq = p.pi_seq
    // join cart_info c on u.ui_seq = c.ci_ui_seq ;
    (value = "select oh from OrderHistoryEntity oh join oh.user u join fetch oh.product where u.seq = :seq")
    //        select c from CartInfoEntity c join c.user u join fetch c.option o join fetch o.product where u.seq = :seq
    List<OrderHistoryEntity> findByUserSeqWithFetch(@Param("seq") Long userSeq);
    public OrderHistoryEntity findByUser(UserEntity user);
    
}



// @Query(value = "select oh from OrderHistoryEntity oh join oh.user u join fetch oh.product c join fetch c.cart where u.seq = :seq")
// List<OrderHistoryEntity> findByUserSeqWithFetch(@Param("seq") Long userSeq);