package com.greenart.firstproject.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "market_info")
@Getter @Setter
public class MarketInfoEntity {

    @Column(name = "mi_seq")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(name = "mi_adress")
    private String address;

    @Column(name = "mi_lat")
    private Double latitude;

    @Column(name = "mi_long")
    private Double longitude;

    @Column(name = "mi_business_num")
    private String businessNumber;

    @Column(name = "mi_delivery_day")
    private String deliveryDay;

    @Column(name = "mi_open_time")
    private String openTime;

    @Column(name = "mi_close_time")
    private String closeTime;

    @Column(name = "mi_delivery_time")
    private String deliveryTime;

    @Column(name = "mi_phone")
    private String phone;

    @OneToMany(mappedBy = "marketInfo")
    private List<AdminEntity> admins = new ArrayList<>();
}
