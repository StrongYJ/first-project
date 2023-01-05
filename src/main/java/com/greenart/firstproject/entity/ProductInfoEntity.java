package com.greenart.firstproject.entity;


import java.util.ArrayList;
import java.util.List;

import com.greenart.firstproject.vo.superadmin.AdminAddProductVO;
import com.greenart.firstproject.vo.superadmin.AdminUpdateProductVO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductInfoEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pi_seq")            
    private Long seq;
    
    @Column(name = "pi_name")           
    private String name;
    
    @Column(name = "pi_type")           
    private String type;
    
    @Column(name = "pi_level")          
    private Double level;
    
    @Column(name = "pi_sweetness")      
    private Integer sweetness;
    
    @Column(name = "pi_sour")           
    private Integer sour;
    
    @Column(name = "pi_soda")           
    private Integer soda;
    
    @Column(name = "pi_raw")            
    private String raw;
    
    @Column(name = "pi_img")            
    private String img;
    
    @Column(name = "pi_sub_name")       
    private String subName;
    
    @Column(name = "pi_detail_img")     
    private String detailImg;
    
    @Column(name = "pi_detail_content") 
    private String detailContent;
    
    @Column(name = "pi_manu")           
    private String manufacturer;

    @OneToMany(mappedBy = "product")
    List<OptionInfoEntity> options = new ArrayList<>();

    @Builder
    public ProductInfoEntity(AdminAddProductVO adminProductInfoVO, String basicImg, String detailImg) {
        this.name = adminProductInfoVO.getName();
        this.type = adminProductInfoVO.getType();
        this.level = adminProductInfoVO.getLevel();
        this.sweetness = adminProductInfoVO.getSweetness();
        this.sour = adminProductInfoVO.getSour();
        this.soda = adminProductInfoVO.getSoda();
        this.raw = adminProductInfoVO.getRaw();
        this.subName = adminProductInfoVO.getSubName();
        this.detailContent = (adminProductInfoVO.getDetailContent().isBlank() ? null : adminProductInfoVO.getDetailContent());
        this.manufacturer = adminProductInfoVO.getManufacturer();
        this.img = basicImg;
        this.detailImg = detailImg;
    }
    
    public void updateProductInfo(AdminUpdateProductVO data) {
        this.name = data.getName();
        this.type = data.getType();
        this.level = data.getLevel();
        this.sweetness = data.getSweetness();
        this.sour = data.getSour();
        this.soda = data.getSoda();
        this.raw = data.getRaw();
        this.subName = data.getSubName();
        this.detailContent = data.getDetailContent();
        this.manufacturer = data.getManufacturer();
    }
}
