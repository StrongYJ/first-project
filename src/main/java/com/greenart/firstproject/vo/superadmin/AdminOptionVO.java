package com.greenart.firstproject.vo.superadmin;

import com.greenart.firstproject.entity.OptionInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminOptionVO {
    private Long seq;
    private String name;
    private Integer price;

    public static AdminOptionVO fromEntity(OptionInfoEntity entity) {
        return AdminOptionVO.builder()
            .seq(entity.getSeq())
            .name(entity.getOption())
            .price(entity.getPrice())
            .build();
    }
}
