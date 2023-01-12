package com.greenart.firstproject.entity;

import java.time.LocalDate;

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

@Getter
@Entity
@Table(name = "coupon_info")
public class CouponInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cou_seq")   
    private Long couSeq;

    @Column(name = "cou_name")   
    private String couName;

    @Column(name = "cou_code")   
    private String couCode;

    @Column(name = "cou_valid_dt")   
    private LocalDate couValidDt;

    @Column(name = "cou_status")   
    private Integer couStatus;

    @JoinColumn(name = "cou_ui_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

}
