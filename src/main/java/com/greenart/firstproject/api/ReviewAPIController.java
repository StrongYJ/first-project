package com.greenart.firstproject.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.greenart.firstproject.config.security.LoginUserSeq;
import com.greenart.firstproject.service.ReviewService;
import com.greenart.firstproject.vo.review.ReviewCreateVO;
import com.greenart.firstproject.vo.review.ReviewUpdateVO;
import com.greenart.firstproject.vo.review.ReviewVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "리뷰", description = "리뷰 서비스를 위한 API")
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewAPIController {
    private final ReviewService reviewService;

    @Operation(summary = "제품별 리뷰 정보", description = "제품에 따른 리뷰 정보")
    @GetMapping("/{seq}")
    public ResponseEntity<Map<String, Object>> getReview(@PathVariable("seq") Long piSeq){
        Map<String, Object> map = new LinkedHashMap<>();
        List<ReviewVO> review = reviewService.getReview(piSeq);
        if(review.size() != 0) {
            map.put("reviewGrade", review.stream().mapToDouble(r -> r.getGrade()).average());
        } else {
            map.put("reviewGrade", null);
        }
        map.put("size", review.size());
        map.put("data", review);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Object> postReview(
        @LoginUserSeq Long userSeq, 
        @RequestPart(name = "body") ReviewCreateVO data,
        @RequestPart(name = "file", required = false) MultipartFile ...images
    ) {
        Map<String, Object> map = new LinkedHashMap<>();
        var addReview = reviewService.addReivew(userSeq, data, images);
        map.put("message", "리뷰 등록 완료");
        map.put("data", addReview);
        return new ResponseEntity<Object>(map, HttpStatus.CREATED);
    }

    @PutMapping("/{reviewSeq}")
    public ResponseEntity<Object> updateReview(@PathVariable("reviewSeq") Long reviewSeq, @RequestBody ReviewUpdateVO data){
        Map<String, Object> map = new LinkedHashMap<>();
        ReviewUpdateVO updateReview = reviewService.updateReview(reviewSeq, data);
        map.put("message", "리뷰 수정 완료");
        map.put("data", updateReview);
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{reviewSeq}")
    public ResponseEntity<Map<String, Object>> deleteReview(@PathVariable("reviewSeq") Long seq){
        Map<String, Object> map = new LinkedHashMap<>();
        reviewService.delete(seq);
        map.put("message", "삭제 완료");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
}
