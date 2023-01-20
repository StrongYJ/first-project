package com.greenart.firstproject.vo.product;

import com.greenart.firstproject.entity.OptionInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OptionVO {
    private Long seq;
    private String name;
    private Integer price;

    public OptionVO(OptionInfoEntity entity) {
        this.seq = entity.getSeq();
        this.name = entity.getOption();
        this.price = entity.getPrice();
    }
}
