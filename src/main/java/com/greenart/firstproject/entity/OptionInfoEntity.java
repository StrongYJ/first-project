package com.greenart.firstproject.entity;


import com.greenart.firstproject.vo.superadmin.AdminOptionVO;
import com.greenart.firstproject.vo.superadmin.AdminUpdateOptionVO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "option_info")
public class OptionInfoEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oi_seq")        
    private Long seq;
    
    @Column(name = "oi_name")     
    private String option;
    
    @Column(name = "oi_price")      
    private Integer price;
    
    @JoinColumn(name = "oi_pi_seq") 
    @ManyToOne(fetch = FetchType.LAZY) // Lazy를 기본으로 해야 조인할때 최적화, eager하지말고(한번에 됨)
    ProductInfoEntity product;
    

    @Builder
    public OptionInfoEntity(AdminOptionVO vo, ProductInfoEntity product) {
        this.option = vo.getName();
        this.price = vo.getPrice();
        this.product = product;
    }

    public void modifyNameAndPrice(String name, Integer price) {
        this.option = name;
        this.price = price;
    }
}
