package com.greenart.firstproject.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_info")
public class ProductInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pi_seq")            private Long seq;
    @Column(name = "pi_name")           private String name;
    @Column(name = "pi_type")           private String type;
    @Column(name = "pi_level")          private Double level;
    @Column(name = "pi_sweetness")      private Integer sweetness;
    @Column(name = "pi_sour")           private Integer sour;
    @Column(name = "pi_soda")           private Integer soda;
    @Column(name = "pi_raw")            private String raw;
    @Column(name = "pi_img")            private String img;
    @Column(name = "pi_sub_name")       private String subName;
    @Column(name = "pi_detail_img")     private String detailImg;
    @Column(name = "pi_detail_content") private String detailContent;
    @Column(name = "pi_manu")           private String manu;
}
