package com.greenart.firstproject.yeongjang;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.greenart.firstproject.entity.ReviewEntity;
import com.greenart.firstproject.repository.ProductInfoRepository;
import com.greenart.firstproject.repository.ReviewRepository;
import com.greenart.firstproject.vo.review.ReviewVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class MyTest {
    @Autowired
    private ReviewRepository reviewRepo;
    
    @Autowired ProductInfoRepository prodRepo;
    @Test
    void jpqlTest() {
        List<ReviewEntity> list = reviewRepo.findByProductReview(1L);
        for(var a : list) {
            log.info("data={}", a.getSeq());
        }
        Assertions.assertThat(list.size()).isEqualTo(4);
        
    }
    @Test
    void jpqlTest2() {
        List<ReviewVO> list = reviewRepo.findByProductReview(1L).stream().map(ReviewVO::fromEntity).toList();
        for(var a : list) {
            log.info("data={}", a);
        }
        Assertions.assertThat(list.size()).isEqualTo(4);
        
    }
}
