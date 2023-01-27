package com.greenart.firstproject.repository;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greenart.firstproject.entity.CouponInfoEntity;
import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.vo.coupon.CouponInfoVO;

@Repository
public interface CouponInfoRefository extends JpaRepository<CouponInfoEntity, Long>{
    List<CouponInfoEntity> findByUser(UserEntity user);

    @Query(value = "select c from CouponInfoEntity c left join fetch c.user where c.user.seq = :uiSeq")
    List<CouponInfoEntity> findByFetchByUserSeq(@Param("uiSeq") Long userSeq);
}
