package com.greenart.firstproject.vo.review;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReviewCreateVO {
    private Long productSeq; // 제품 번호 - 결재 내역에서 가져와야 함
    private String optionName;
    private Double grade; // 평점
    private String content; // 리뷰 내용
    private LocalDateTime regDt; // 리뷰 등록 날짜
}
