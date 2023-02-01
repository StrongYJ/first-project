package com.greenart.firstproject.vo.review;

import lombok.Data;

@Data
public class ReviewUpdateVO {
    private Double grade; // 평점
    private String content; // 리뷰 내용
}
