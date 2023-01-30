package com.greenart.firstproject.vo.mileage;

import java.time.LocalDate;

import com.greenart.firstproject.entity.MileagePointEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MileageInfoVO {
    private String email;
    private String nickname;
    private Integer mpPrice;
    private LocalDate mpExpirationDate;
    
    public static MileageInfoVO fromEntity(MileagePointEntity entity) {
        return MileageInfoVO.builder()
        .email(entity.getUser().getEmail())
        .nickname(entity.getUser().getNickname())
        .mpPrice(entity.getMpPrice())
        .mpExpirationDate(entity.getMpExpirationDate())
        .build();
    }

    //seq번호
    public MileageInfoVO(MileagePointEntity entity) {
        this.email = email;
        this.nickname = nickname;
        this.mpPrice = entity.getMpPrice();
        this.mpExpirationDate = entity.getMpExpirationDate();
    }
}
