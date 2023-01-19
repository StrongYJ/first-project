package com.greenart.firstproject.vo.product;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;

@Data
public class ProductMainVO {
    private Long seq;
    private String name;
    private String subName;
    private Integer mainPrice;
    private String basicImg;
    private Double reviewGrade;
    private Long reviewNumber;

    @QueryProjection
    public ProductMainVO(Long seq, String name, String subName, Integer mainPrice, String basicImg) {
        this.seq = seq;
        this.name = name;
        this.subName = subName;
        this.mainPrice = mainPrice;
        this.basicImg = basicImg;
        this.reviewGrade = null;
        this.reviewNumber = null;
    }

    @QueryProjection
    public ProductMainVO(Long seq, String name, String subName, Integer mainPrice, String basicImg, Double reviewGrade,
            Long reviewNumber) {
        this.seq = seq;
        this.name = name;
        this.subName = subName;
        this.mainPrice = mainPrice;
        this.basicImg = basicImg;
        this.reviewGrade = reviewGrade;
        this.reviewNumber = reviewNumber;
    }

    
    
}
