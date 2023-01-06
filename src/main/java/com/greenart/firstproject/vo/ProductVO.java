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

    public static ProductVO fromEntity(ProductInfoEntity p) { 
        return ProductVO.builder()
                        .seq(p.getSeq())
                        .name(p.getName())
                        .type(p.getType())
                        .level(p.getLevel())
                        .sweetness(p.getSweetness())
                        .sour(p.getSour())
                        .soda(p.getSoda())
                        .raw(p.getRaw())
                        .img(p.getImg())
                        .subName(p.getSubName())
                        .detailImg(p.getDetailImg())
                        .detailContent(p.getDetailContent())
                        .manufacturer(p.getManu())
                        .build();

            // .cateSeq(b.getCateSeq())
            // .wiSeq(b.getWiSeq())
            // .title(b.getTitle())
            // .price(b.getPrice())
            // .publisher(b.getPublisher())
            // .publishDt(b.getPublishDt())
            // .deliveryPrice(b.getDeliveryPrice())
            // .contentTitle(b.getContentTitle())
            // .contentText(b.getContentText())
            // .imageUri("/api/book/image/basic/" + b.getImgSeq())
            // .detailImageUri(b.getDImgSeq() == null ? null : ("/api/book/image/detail/" + b.getDImgSeq()))
            // .build();
    }
}
// seq
// name
// type
// level
// sweetness
// sour
// soda
// raw
// img
// subName
// detailImg
// detailContent
// manu