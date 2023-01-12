package com.greenart.firstproject.vo;

import java.time.LocalDateTime;

import com.greenart.firstproject.entity.ReviewEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ReviewVO {
    private String nickname;
    private Double grade;
    private String content;
    private LocalDateTime regDt;
    private String option;

    public static ReviewVO fromEntity(ReviewEntity entity) {
        return ReviewVO.builder()
            .nickname(entity.getUser().getNickname())
            .grade(entity.getGrade())
            .content(entity.getContent())
            .regDt(entity.getRegDt())
            .option(entity.getOption().getOption())
            .build();
    }
}
