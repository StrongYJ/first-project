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
    private String email;
    private String nickname;
    private String couName;
    private String couCode;
    private LocalDate couValidDt;
    private Integer couStatus;
    
    public static CouponInfoVO fromEntity(CouponInfoEntity entity) {
        return CouponInfoVO.builder()
        .email(entity.getUser().getEmail())
        .nickname(entity.getUser().getNickname())
        .couName(entity.getCouName())
        .couCode(entity.getCouCode())
        .couValidDt(entity.getCouValidDt())
        .couStatus(entity.getCouStatus())
        .build();
    }
    
    //seq번호
    public CouponInfoVO(CouponInfoEntity entity) {
        this.email = email;
        this.nickname = nickname;
        this.couName = entity.getCouName();
        this.couValidDt = entity.getCouValidDt();
        this.couCode = entity.getCouCode();
        this.couStatus = entity.getCouStatus();

    }
}
