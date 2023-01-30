package com.greenart.firstproject.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentInfoEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_seq")
    private Long seq;

    @Column(name = "pay_original")
    private Integer originalPrice;

    @Column(name = "pay_final")
    private Integer finalPrice;

    @Column(name = "pay_order_dt")
    private LocalDateTime orderDt;

    @JoinColumn(name = "pay_ui_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @OneToMany(mappedBy = "paymentInfo")
    private List<OrderHistoryEntity> orderHistories = new ArrayList<>();
}
