package com.greenart.firstproject.vo.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "장바구니에 추가, 수량 수정을 위한")
public class CartPlusMinusVO {

    @Schema(description = "옵션의 seq번호")
    private Long optionSeq;
    @Schema(description = "수량")
    private Integer quantity;
}
