package com.greenart.firstproject.entity;

import java.time.LocalDateTime;

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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderHistoryEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oh_seq")
    private Long seq;

    @Column(name = "oh_oi_name")
    private String name;
    
    @Column(name = "oh_order_dt")
    private LocalDateTime orderDt;

    @Column(name = "oh_quantity")
    private Integer quantity;

    @Column(name = "oh_price")
    private Integer price;

    @Column(name = "oh_delivery_status")
    private Integer deliveryStatus;

    @Column(name = "oh_canceled")
    private Boolean canceled;

    @JoinColumn(name = "oh_ui_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @JoinColumn(name = "oh_pi_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductInfoEntity product;

}
