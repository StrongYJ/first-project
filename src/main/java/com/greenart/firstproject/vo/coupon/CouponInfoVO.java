package com.greenart.firstproject.vo.coupon;

import java.time.LocalDate;

import com.greenart.firstproject.entity.CouponInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponInfoVO {
    private Long couponSeq;
    private String email;
    private String nickname;
    private String couName;
    private String couCode;
    private LocalDate couValidDt;
    private Integer couStatus;
    private Double discountRate;
    
    public static CouponInfoVO fromEntity(CouponInfoEntity entity) {
        return CouponInfoVO.builder()
        .email(entity.getUser().getEmail())
        .nickname(entity.getUser().getNickname())
        .couName(entity.getCouName())
        .couCode(entity.getCouCode())
        .couValidDt(entity.getCouValidDt())
        .couStatus(entity.getCouStatus())
        .discountRate(entity.getDiscountRate())
        .build();
    }
    
    //seq번호
    public CouponInfoVO(CouponInfoEntity entity) {
        this.couponSeq = entity.getCouSeq();
        this.email = entity.getUser().getEmail();
        this.nickname = entity.getUser().getNickname();
        this.couName = entity.getCouName();
        this.couValidDt = entity.getCouValidDt();
        this.couCode = entity.getCouCode();
        this.couStatus = entity.getCouStatus();
        this.discountRate = entity.getDiscountRate();

    }
}
