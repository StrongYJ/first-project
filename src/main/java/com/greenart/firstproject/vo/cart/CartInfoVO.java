package com.greenart.firstproject.vo.cart;

import com.greenart.firstproject.entity.CartInfoEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
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

    public CartInfoVO(CartInfoEntity entity) {        
        this.productName = entity.getOption().getProduct().getName();
        this.optionSeq = entity.getOption().getSeq();
        this.optionName = entity.getOption().getOption();
        this.quantity = entity.getQuantity();
    }
    
}
