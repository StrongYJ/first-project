package com.greenart.firstproject.vo;

import com.greenart.firstproject.entity.OptionInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OptionVO {
    private Long seq;
    private String option;
    private Integer price;
    private Long piSeq;

    public OptionVO(OptionInfoEntity entity) {
        this.seq=entity.getSeq();
        this.option=entity.getOption();
        this.price=entity.getPrice();
        this.piSeq=entity.getSeq();
    }
}
