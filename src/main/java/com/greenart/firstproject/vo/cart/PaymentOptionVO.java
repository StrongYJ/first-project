package com.greenart.firstproject.vo.cart;

import com.greenart.firstproject.entity.OptionInfoEntity;
import com.greenart.firstproject.entity.OrderHistoryEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PaymentOptionVO {
    private Long seq;
    private String optionName;
    private Integer price;

    public PaymentOptionVO(OrderHistoryEntity entity) {
        this.seq = entity.getSeq();
        this.optionName = entity.getName() ;
        this.price = entity.getPrice();
    }
}
