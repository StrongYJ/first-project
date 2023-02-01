package com.greenart.firstproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.entity.ReviewEntity;
import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.repository.OrderHistoryRepository;
import com.greenart.firstproject.repository.ProductInfoRepository;
import com.greenart.firstproject.repository.ReviewRepository;
import com.greenart.firstproject.repository.UserRepository;
import com.greenart.firstproject.vo.review.ReviewCreateVO;
import com.greenart.firstproject.vo.review.ReviewUpdateVO;
import com.greenart.firstproject.vo.review.ReviewVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepo;
    private final UserRepository userRepo;
    private final ProductInfoRepository productInfoRepo;
    
    public List<ReviewVO> getReview(Long piSeq){
        // return reviewRepo.findVOByProductSeq(piSeq);
        return reviewRepo.findFetchByProductSeq(piSeq).stream().map(ReviewVO::new).toList();
    }

    public ReviewCreateVO addReivew(Long userSeq, ReviewCreateVO data) {
        // 사용자 정보를 불러오고 없으면 예외 발생
        UserEntity user = userRepo.findById(userSeq).orElseThrow();
        // 제품 정보를 불러오고 없으면 예외 발생
        ProductInfoEntity product = productInfoRepo.findById(data.getProductSeq()).orElseThrow();
        
        ReviewEntity addReview = new ReviewEntity(data);
        addReview.setProduct(product);
        addReview.setUser(user);
        reviewRepo.save(addReview);
        data.setRegDt(addReview.getRegDt());
        return data;
    }

    public ReviewUpdateVO updateReview(Long reviewSeq, ReviewUpdateVO data){
        // 리뷰 정보를 불러오고 없으면 예외 발생
        ReviewEntity updateReview = reviewRepo.findById(reviewSeq).orElseThrow();
        updateReview.setUpdateReview(data);
        reviewRepo.save(updateReview);
        return data;
    }

    public void delete(Long seq){
        reviewRepo.delete(reviewRepo.findById(seq).orElseThrow());
    }

}
