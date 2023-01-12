package com.greenart.firstproject.vo.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartPlusMinusVO {

    private Long optionSeq;
    private Integer stock;
}
