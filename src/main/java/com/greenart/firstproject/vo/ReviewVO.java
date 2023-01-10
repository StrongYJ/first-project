package com.greenart.firstproject.vo;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ReviewVO {
    private String nickname;
    private String option;
    private Double grade;
    private String content;
    private LocalDateTime regDt;
}
