package com.greenart.firstproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greenart.firstproject.entity.OptionInfoEntity;
import com.greenart.firstproject.entity.ReviewEntity;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>{
    List<ReviewEntity> findByOption(OptionInfoEntity option);
    @Query("select r from ReviewEntity r join r.option o where o.product.seq = :productSeq")
    List<ReviewEntity> findByProductSeq(@Param("productSeq") Long productSeq);
}
/*
 * 리뷰에 옵션 FK가 있음
 * 내가 필요한건 제품의 seq
 * 옵션 번호를 따라가서 제품seq를 찾아야함
 * 
 * 리뷰(옵션seq FK)-옵션(제품seq FK)-제품seq(파라미터로 받음)
 * 파라미터로 제품seq 받으면
 * 제품seq와 옵션에 있는 제품FK와 같은 것을 찾아야함
 * 그 제품FK에 있는 모든 리뷰를 출력
 */