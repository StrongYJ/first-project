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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "coupon_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(name = "cou_discount_rate")
    private Double discountRate;
    
    @JoinColumn(name = "cou_ui_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    public CouponInfoEntity(String couName, String couCode, LocalDate couValidDt, Double discountRate,
            UserEntity user) {
        this.couName = couName;
        this.couCode = couCode;
        this.couValidDt = couValidDt;
        this.discountRate = discountRate;
        this.user = user;
    }

    public void changeStatus(int status) {
        this.couStatus = status;
    }

    @PrePersist
    private void defaultStatus() {
        this.couStatus = 1;
    }

}
