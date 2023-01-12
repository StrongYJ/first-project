package com.greenart.firstproject.vo.cart;

import com.greenart.firstproject.entity.CartInfoEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "장바구니 정보")
public class CartInfoVO {
    
    @Schema(description = "제품 이름")
    private String productName;
    @Schema(description = "옵션 seq번호")
    private Long optionSeq;
    @Schema(description = "옵션 이름")
    private String optionName;
    @Schema(description = "수량")
    private Integer quantity;

    public static CartInfoVO fromEntity(CartInfoEntity entity) {
        return CartInfoVO.builder()
        .productName(entity.getOption().getProduct().getName())
        .optionSeq(entity.getOption().getSeq())
        .optionName(entity.getOption().getOption())
        .quantity(entity.getQuantity())
        .build();
    }
}
