package com.greenart.firstproject.vo.review;

import java.time.LocalDateTime;

import com.greenart.firstproject.entity.ReviewEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Schema(name = "리뷰 정보", description = "리뷰 정보를 담는 vo")
@Data
@Builder
@AllArgsConstructor
public class ReviewVO {
    @Schema(description = "회원 닉네임")
    private String nickname;
    @Schema(description = "평점")
    private Double grade;
    @Schema(description = "내용")
    private String content;
    @Schema(description = "등록일")
    private LocalDateTime regDt;
    @Schema(description = "구매했던 제품의 옵션이름")
    private String optionName;

    public ReviewVO(ReviewEntity entity) {
        String nickname = entity.getUser() != null ? entity.getUser().getNickname() : "탈퇴한 유저";
        this.nickname = nickname;
        this.grade = entity.getGrade();
        this.content = entity.getContent();
        this.regDt = entity.getRegDt();
        this.optionName = entity.getOptionName();
    }
}
