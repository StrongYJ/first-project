package com.greenart.firstproject.vo.cart;

import com.greenart.firstproject.entity.CartInfoEntity;
import com.greenart.firstproject.entity.OptionInfoEntity;
import com.greenart.firstproject.entity.ProductInfoEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "장바구니 정보")
public class CartInfoVO {
    
    @Schema(description = "제품 이름")
    private String productName;
    @Schema(description = "제품 썸네일")
    private String thumbImg;
    @Schema(description = "옵션 seq번호")
    private Long optionSeq;
    @Schema(description = "옵션 이름")
    private String optionName;
    @Schema(description = "옵션 가격")
    private Integer optionPrice;
    @Schema(description = "수량")
    private Integer quantity;

    public CartInfoVO(CartInfoEntity entity) {
        OptionInfoEntity option = entity.getOption();
        ProductInfoEntity product = option.getProduct();
        this.productName = product.getName();
        this.thumbImg = "/api/images/product/" + product.getImg();
        this.optionSeq = option.getSeq();
        this.optionName = option.getOption();
        this.optionPrice = option.getPrice();
        this.quantity = entity.getQuantity();
    }
    
}
