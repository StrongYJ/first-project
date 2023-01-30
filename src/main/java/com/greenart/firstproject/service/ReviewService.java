package com.greenart.firstproject.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.greenart.firstproject.entity.OrderHistoryEntity;
import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.entity.ReviewEntity;
import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.repository.OrderHistoryRepository;
import com.greenart.firstproject.repository.ProductInfoRepository;
import com.greenart.firstproject.repository.ReviewRepository;
import com.greenart.firstproject.repository.UserRepository;
import com.greenart.firstproject.vo.review.ReviewCreateVO;
import com.greenart.firstproject.vo.review.ReviewVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepo;
    private final UserRepository userRepo;
    private final ProductInfoRepository productInfoRepo;
    private final OrderHistoryRepository orderHistoryRepo;
    
    public List<ReviewVO> getReview(Long piSeq){
        // return reviewRepo.findVOByProductSeq(piSeq);
        return reviewRepo.findFetchByProductSeq(piSeq).stream().map(ReviewVO::new).toList();
    }
    /**
     * 리뷰등록 메서드
     * @param userSeq 유저번호
     * @param data dto
     * @return
     */
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

    public void delete(Long seq){
        reviewRepo.delete(reviewRepo.findById(seq).orElseThrow());
    }

}
