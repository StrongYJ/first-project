package com.greenart.firstproject.vo.cart;

import com.greenart.firstproject.entity.CartInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartInfoVO {
    
    private String productName;
    private Long optionSeq;
    private String optionName;
    private Integer stock;

    public static CartInfoVO fromEntity(CartInfoEntity entity) {
        return CartInfoVO.builder()
        .productName(entity.getOption().getProduct().getName())
        .optionSeq(entity.getOption().getSeq())
        .optionName(entity.getOption().getOption())
        .stock(entity.getStock())
        .build();
    }
}
