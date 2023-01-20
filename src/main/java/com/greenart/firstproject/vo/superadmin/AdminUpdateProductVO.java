package com.greenart.firstproject.vo.superadmin;


import org.springframework.web.multipart.MultipartFile;

import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.entity.enums.AlcoholType;
import com.greenart.firstproject.entity.enums.RawMaterial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUpdateProductVO {
    private Long seq;
    private String name;
    private AlcoholType type;
    private Double level;
    private Integer sweetness;
    private Integer sour;
    private Integer soda;
    private RawMaterial raw;
    private String subName;
    private String detailContent;
    private String manufacturer;
    private MultipartFile basicImg;
    private MultipartFile detailImg;
    private String basicImgPath;
    private String detailImgPath;

    public static AdminUpdateProductVO fromEntity(ProductInfoEntity prod) {
        return AdminUpdateProductVO.builder()
                .seq(prod.getSeq())
                .name(prod.getName())
                .type(prod.getType())
                .level(prod.getLevel())
                .sweetness(prod.getSweetness())
                .sour(prod.getSour())
                .soda(prod.getSoda())
                .raw(prod.getRaw())
                .subName(prod.getSubName())
                .detailContent(prod.getDetailContent())
                .manufacturer(prod.getManufacturer())
                .basicImgPath("/api/images/product/" + prod.getImg())
                .detailImgPath("/api/images/product/" + prod.getDetailImg())
                .build();
    }
}
