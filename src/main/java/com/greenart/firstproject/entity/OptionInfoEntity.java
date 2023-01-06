package com.greenart.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "option_info")
public class OptionInfoEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oi_seq")        
    private Long seq;
    
    @Column(name = "oi_option")     
    private String option;
    
    @Column(name = "oi_price")      
    private Integer price;
    
    @JoinColumn(name = "oi_pi_seq") 
    @ManyToOne(fetch = FetchType.LAZY) // Lazy를 기본으로 해야 조인할때 최적화, eager하지말고(한번에 됨)
    ProductInfoEntity product;
    
}
