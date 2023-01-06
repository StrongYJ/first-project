package com.greenart.firstproject.vo.superadmin;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminAddProductVO {
    private String name;
    private String type;
    private Double level;
    private Integer sweetness;
    private Integer sour;
    private Integer soda;
    private String raw;
    private String subName;
    private String detailContent;
    private String manufacturer;
    private MultipartFile basicImg;
    private MultipartFile detailImg;
    private String basicImgPath;
    private String detailImgPath;
}
