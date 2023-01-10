package com.greenart.firstproject.vo;

import com.greenart.firstproject.entity.ProductInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductVO {
    private Long seq;
    private String name;
    private String type;
    private Double level;
    private Integer sweetness;
    private Integer sour;
    private Integer soda;
    private String raw;
    private String img;
    private String subName;
    private String detailImg;
    private String detailContent;
    private String manufacturer;
    
    public ProductVO(ProductInfoEntity entity) {
        this.seq=entity.getSeq();
        this.name=entity.getName();
        this.type=entity.getType();
        this.level=entity.getLevel();
        this.sweetness=entity.getSweetness();
        this.sour=entity.getSour();
        this.soda=entity.getSoda();
        this.raw=entity.getRaw();
        this.img=entity.getImg();
        this.subName=entity.getSubName();
        this.detailImg=entity.getDetailImg();
        this.detailContent=entity.getDetailContent();
        this.manufacturer=entity.getManu();
    }
}

