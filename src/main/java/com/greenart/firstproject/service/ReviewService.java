package com.greenart.firstproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.repository.ReviewRepository;
import com.greenart.firstproject.vo.review.ReviewCreateVO;
import com.greenart.firstproject.vo.review.ReviewVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepo;

    public List<ReviewVO> getReview(Long piSeq){
        // return reviewRepo.findVOByProductSeq(piSeq);
        return reviewRepo.findFetchByProductSeq(piSeq).stream().map(ReviewVO::new).toList();
    }

    public ReviewCreateVO createReivew(UserEntity loginUser, ReviewCreateVO data) {
        
        return null;
    }

}
