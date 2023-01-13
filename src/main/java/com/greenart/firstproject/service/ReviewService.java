package com.greenart.firstproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.greenart.firstproject.repository.ReviewRepository;
import com.greenart.firstproject.vo.review.ReviewVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepo;

    public List<ReviewVO> getReview(Long piSeq){
        return reviewRepo.findVOByProductSeq(piSeq);
    }
}
