package com.greenart.firstproject.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.greenart.firstproject.entity.OptionInfoEntity;
import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.entity.ReviewEntity;
import com.greenart.firstproject.repository.OptionInfoRepository;
import com.greenart.firstproject.repository.ProductInfoRepository;
import com.greenart.firstproject.repository.ReviewRepository;
import com.greenart.firstproject.vo.ReviewVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepo;

    public Map<String, Object> getReview(Long piSeq, ReviewVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        List<ReviewEntity> list = reviewRepo.findByProductSeq(piSeq);
        if(list.isEmpty()){
            resultMap.put("status", false);
            resultMap.put("message", "등록된 리뷰가 없습니다.");
            resultMap.put("code", HttpStatus.NOT_FOUND);
        }
        resultMap.put("status", true);
        resultMap.put("message", "리뷰 표출");
        resultMap.put("code", HttpStatus.OK);
        resultMap.put("list", list);
        return resultMap;
    }
}
