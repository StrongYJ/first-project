package com.greenart.firstproject.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.service.ReviewService;
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
    @GetMapping("")
    public ResponseEntity<List<ReviewVO>> getReview(@RequestParam Long piSeq){
        return new ResponseEntity<>(reviewService.getReview(piSeq), HttpStatus.OK);
    }
}
