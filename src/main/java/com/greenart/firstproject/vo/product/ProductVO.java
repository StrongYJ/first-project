package com.greenart.firstproject.vo.product;

import java.util.List;

import com.greenart.firstproject.entity.OptionInfoEntity;
import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.entity.enums.AlcoholType;
import com.greenart.firstproject.entity.enums.RawMaterial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductVO {
    private Long productSeq;
    private String productName;
    private String type;
    private Double level;
    private Integer sweetness;
    private Integer sour;
    private Integer soda;
    private String raw;
    private String thumbImg;
    private String subName;
    private String detailImg;
    private String detailContent;
    private String manufacturer;
    private List<OptionVO> options;
    
    public ProductVO(ProductInfoEntity product) {
        this.productSeq = product.getSeq();
        this.productName = product.getName();
        this.subName = product.getSubName();
        this.type = product.getType().getTitle();
        this.level = product.getLevel();
        this.sweetness = product.getSweetness();
        this.sour = product.getSour();
        this.soda = product.getSoda();
        this.raw = product.getRaw().getTitle();
        this.manufacturer = product.getManufacturer();
        this.detailContent = product.getDetailContent();
        this.thumbImg = "/api/images/product/" + product.getImg();
        this.detailImg = "/api/images/product/" + product.getDetailImg();
        this.options = product.getOptions().stream().map(OptionVO::new).toList();
    }
}

