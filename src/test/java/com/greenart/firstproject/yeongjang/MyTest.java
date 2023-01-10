package com.greenart.firstproject.yeongjang;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.greenart.firstproject.entity.ReviewEntity;
import com.greenart.firstproject.repository.ReviewRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class MyTest {
    @Autowired
    private ReviewRepository reviewRepo;
    
    @Test
    void jpqlTest() {
        Assertions.assertThat(reviewRepo.findByProductSeq(4L).size()).isEqualTo(2);
        for(ReviewEntity a : reviewRepo.findByProductSeq(4L)) {
            log.info("data = {}", a.getContent());
        }
    }
}
