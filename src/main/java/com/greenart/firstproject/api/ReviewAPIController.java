package com.greenart.firstproject.api;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewAPIController {
    private final ReviewService reviewService;

    @GetMapping("/reviewList")
    public Map<String, Object> getReview(@RequestParam Long piSeq){
        Map<String, Object> resultMap = reviewService.getReview(piSeq);
        return resultMap;
    }
}
