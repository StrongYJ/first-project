package com.greenart.firstproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greenart.firstproject.entity.OptionInfoEntity;
import com.greenart.firstproject.entity.ReviewEntity;
import com.greenart.firstproject.vo.review.ReviewVO;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>{
    // List<ReviewEntity> findByOption(OptionInfoEntity option);
    // @Query(value = "select a.* from review_info a join option_info b on a.ri_oi_seq = b.oi_seq where b.oi_pi_seq = :piSeq", nativeQuery = true)
    // List<ReviewEntity> findByProductReview(@Param("piSeq") Long piSeq);

    @Query(value = "select new com.greenart.firstproject.vo.review.ReviewVO(u.nickname, r.grade, r.content, r.regDt, r.optionName) from ReviewEntity r left join UserEntity u on r.user = u where r.product.seq = :piSeq")
    List<ReviewVO> findVOByProductSeq(@Param("piSeq") Long seq);

    @Query(value = "select r from ReviewEntity r join r.product p left join fetch r.user where p.seq = :piSeq")
    List<ReviewEntity> findFetchByProductSeq(@Param("piSeq") Long productSeq);
    // @Query(value = "select ui_nickname as nickname, ri_grade as grade, ri_content as content, ri_reg_dt as regDt, oi_option as option from review_info a join option_info b on a.ri_oi_seq = b.oi_seq join user_info d on d.ui_seq = a.ri_ui_seq join product_info c on b.oi_pi_seq = c.pi_seq where pi_seq = :piSeq", nativeQuery = true)
    // List<ReviewVO> findByProductReview(@Param("piSeq") Long piSeq);
}

/*
 * select r from ReviewEntity r inner join r.option o where o.product = :product
 * 
 * select ui_nickname, ri_grade, ri_content, ri_reg_dt, oi_option from review_info a
join option_info b on a.ri_oi_seq = b.oi_seq
join user_info d on d.ui_seq = a.ri_ui_seq
join product_info c on b.oi_pi_seq = c.pi_seq where pi_seq = 1;

 * select r.grade, r.content, r.regDt, o.option from ReviewEntity r inner join OptionInfoEntity o on r.oiSeq = o.seq inner join ProductInfoEntity p on o.seq = :p.seq 
 * ????????? ?????? FK??? ??????
 * ?????? ???????????? ????????? seq
 * ?????? ????????? ???????????? ??????seq??? ????????????
 * 
 * ??????(??????seq FK)-??????(??????seq FK)-??????seq(??????????????? ??????)
 * ??????????????? ??????seq ?????????
 * ??????seq??? ????????? ?????? ??????FK??? ?????? ?????? ????????????
 * ??? ??????FK??? ?????? ?????? ????????? ??????
 */