package com.greenart.firstproject.vo.superadmin;

import com.greenart.firstproject.entity.ProductInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminMainProductInfoVO {
    private Long seq;
    private String name;
    private String type;
    private Integer stock;

    public static AdminMainProductInfoVO fromEntity(ProductInfoEntity product) {
        return AdminMainProductInfoVO.builder()
        .seq(product.getSeq())
        .name(product.getName())
        .type(product.getType())
        .build();
    }
}
