package com.greenart.firstproject.vo.adminVOs;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AdminProductAddVO {
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
}
