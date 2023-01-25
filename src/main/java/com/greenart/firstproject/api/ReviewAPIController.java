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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.config.MySessionkeys;
import com.greenart.firstproject.entity.ReviewEntity;
import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.service.ReviewService;
import com.greenart.firstproject.vo.review.ReviewCreateVO;
import com.greenart.firstproject.vo.review.ReviewVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
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
        map.put("size", review.size());
        map.put("data", review);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ReviewCreateVO> postReview(HttpSession session, ReviewCreateVO data) {
        Object loginUser = session.getAttribute(MySessionkeys.USER_LOGIN_KEY);
        if(loginUser == null || (loginUser instanceof UserEntity == false)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        ReviewCreateVO created = reviewService.createReivew((UserEntity)loginUser, data);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/{seq}")
    public ResponseEntity<Map<String, Object>> deleteReview(@PathVariable("seq") Long seq){
        Map<String, Object> map = new LinkedHashMap<>();
        reviewService.delete(seq);
        map.put("message", "삭제 완료");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
}
