package com.greenart.firstproject.vo.superadmin;

import org.springframework.web.multipart.MultipartFile;

import com.greenart.firstproject.entity.enums.AlcoholType;
import com.greenart.firstproject.entity.enums.RawMaterial;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminAddProductVO {

    @NotBlank
    private String name;
    
    @NotNull(message = "주종을 선택해주세요.")
    private AlcoholType type;

    @NotNull
    @DecimalMin("0.0") @DecimalMax("100.0")
    private Double level;
    
    @NotNull
    @Min(0) @Max(3)
    private Integer sweetness;

    @NotNull
    @Min(0) @Max(3)
    private Integer sour;

    @NotNull
    @Min(0) @Max(3)
    private Integer soda;

    @NotNull(message = "원료를 선택해주세요.")
    private RawMaterial raw;

    @NotBlank
    private String subName;
    
    private String detailContent;

    @NotBlank
    private String manufacturer;

    @NotNull(message = "이미지를 첨부해주세요.")
    private MultipartFile basicImg;

    @NotNull(message = "이미지를 첨부해주세요.")
    private MultipartFile detailImg;
    private String basicImgPath;
    private String detailImgPath;
}
