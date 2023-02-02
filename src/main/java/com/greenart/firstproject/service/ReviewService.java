package com.greenart.firstproject.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.entity.ReviewEntity;
import com.greenart.firstproject.entity.ReviewImgEntity;
import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.repository.OrderHistoryRepository;
import com.greenart.firstproject.repository.ProductInfoRepository;
import com.greenart.firstproject.repository.ReviewImgRepository;
import com.greenart.firstproject.repository.ReviewRepository;
import com.greenart.firstproject.repository.UserRepository;
import com.greenart.firstproject.util.FileStore;
import com.greenart.firstproject.util.UploadFile;
import com.greenart.firstproject.vo.review.ReviewCreateVO;
import com.greenart.firstproject.vo.review.ReviewResponseVO;
import com.greenart.firstproject.vo.review.ReviewUpdateVO;
import com.greenart.firstproject.vo.review.ReviewVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepo;
    private final UserRepository userRepo;
    private final ProductInfoRepository productInfoRepo;
    private final FileStore fileStore;
    private final ReviewImgRepository imgRepository;
    
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

    @Transactional
    public ReviewResponseVO addReivew(Long userSeq, ReviewCreateVO data, MultipartFile ...images) {
        // 사용자 정보를 불러오고 없으면 예외 발생
        UserEntity user = userRepo.findById(userSeq).orElseThrow();
        // 제품 정보를 불러오고 없으면 예외 발생
        ProductInfoEntity product = productInfoRepo.findById(data.getProductSeq()).orElseThrow();
        ReviewEntity addReview = new ReviewEntity(data, user, product);
        reviewRepo.save(addReview);
        
        if(!ObjectUtils.isEmpty(images)) {
            List<UploadFile> imgs = fileStore.storeFiles("review", images);
            List<ReviewImgEntity> addReviewImages = imgs.stream().map(f -> new ReviewImgEntity(f.getStoreFilename(), addReview)).toList();
            imgRepository.saveAll(addReviewImages);
        }
        
        return new ReviewResponseVO(addReview.getSeq(), addReview.getOptionName(), addReview.getGrade(), addReview.getContent(), addReview.getRegDt().toString());
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
