package com.greenart.firstproject.vo;

import com.greenart.firstproject.entity.OptionInfoEntity;
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

    private String option;
    private Integer price;
    private ProductInfoEntity product;
    
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
        this.manufacturer=entity.getManufacturer();
    }
    public void ProductVoOptionVO(ProductInfoEntity pEntity, OptionInfoEntity oEntity) {
        this.seq=pEntity.getSeq();
        this.name=pEntity.getName();
        this.type=pEntity.getType();
        this.level=pEntity.getLevel();
        this.sweetness=pEntity.getSweetness();
        this.sour=pEntity.getSour();
        this.soda=pEntity.getSoda();
        this.raw=pEntity.getRaw();
        this.img=pEntity.getImg();
        this.subName=pEntity.getSubName();
        this.detailImg=pEntity.getDetailImg();
        this.detailContent=pEntity.getDetailContent();
        this.manufacturer=pEntity.getManufacturer();
        this.option=oEntity.getOption();
        this.price=oEntity.getPrice();
        this.product=oEntity.getProduct();
    }
}

