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
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.config.MySessionkeys;
import com.greenart.firstproject.entity.ReviewEntity;
import com.greenart.firstproject.entity.UserEntity;
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
    public ResponseEntity<Object> postReview(@RequestParam Long userSeq, @RequestBody ReviewCreateVO data) {
        /* 
         * Long seq를 회원 seq로 생각합시다.
         * 회원 주문 내역에서 제품 외래키를 가져옴
         * 그 제품 seq를 받아와서 주문내역 번호 가져옴
         * 그에 해당하는 제품에 리뷰 등록
         * 외래키 회원 번호, 제품 번호 등록해야함
         */
        Map<String, Object> map = new LinkedHashMap<>();
        ReviewCreateVO addReview = reviewService.addReivew(userSeq, data);
        map.put("message", "리뷰 등록 완료");
        map.put("data", addReview);
        return new ResponseEntity<Object>(map, HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<Object> updateReview(@RequestParam Long reviewSeq, @RequestBody ReviewUpdateVO data){
        Map<String, Object> map = new LinkedHashMap<>();
        ReviewUpdateVO updateReview = reviewService.updateReview(reviewSeq, data);
        map.put("message", "리뷰 수정 완료");
        map.put("data", updateReview);
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{seq}")
    public ResponseEntity<Map<String, Object>> deleteReview(@PathVariable("seq") Long seq){
        Map<String, Object> map = new LinkedHashMap<>();
        reviewService.delete(seq);
        map.put("message", "삭제 완료");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
}
