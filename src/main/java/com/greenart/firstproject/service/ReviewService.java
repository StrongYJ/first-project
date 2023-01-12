package com.greenart.firstproject.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenart.firstproject.entity.ReviewEntity;
import com.greenart.firstproject.repository.ReviewRepository;
import com.greenart.firstproject.vo.ReviewVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepo;

    public Map<String, Object> getReview(@RequestParam Long piSeq){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        List<ReviewVO> list = reviewRepo.findByProductReview(piSeq).stream().map(ReviewVO::fromEntity).toList();
        
        if(list.isEmpty()){
            resultMap.put("status", false);
            resultMap.put("message", "등록된 리뷰가 없습니다.");
            resultMap.put("code", HttpStatus.NOT_FOUND);
            return resultMap;
        }
        resultMap.put("status", true);
        resultMap.put("message", "리뷰 표출");
        resultMap.put("code", HttpStatus.OK);
        resultMap.put("list", list);
        return resultMap;
    }
}
