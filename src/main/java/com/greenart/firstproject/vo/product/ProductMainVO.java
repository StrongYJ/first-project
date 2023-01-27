package com.greenart.firstproject.vo.product;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;

@Data
public class ProductMainVO {
    private Long productSeq;
    private String productName;
    private String subName;
    private Integer mainPrice;
    private String thumbImg;
    private Double reviewGrade;
    private Long reviewNumber;

    @QueryProjection
    public ProductMainVO(Long seq, String name, String subName, Integer mainPrice, String basicImg) {
        this.productSeq = seq;
        this.productName = name;
        this.subName = subName;
        this.mainPrice = mainPrice;
        this.thumbImg = basicImg;
        this.reviewGrade = null;
        this.reviewNumber = null;
    }

    @QueryProjection
    public ProductMainVO(Long seq, String name, String subName, Integer mainPrice, String basicImg, Double reviewGrade,
            Long reviewNumber) {
        this.productSeq = seq;
        this.productName = name;
        this.subName = subName;
        this.mainPrice = mainPrice;
        this.thumbImg = "/api/images/product/" + basicImg;
        this.reviewGrade = reviewGrade;
        this.reviewNumber = reviewNumber;
    }

    
    
}
