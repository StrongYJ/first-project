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
import lombok.AccessLevel;
import lombok.Builder;
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
    
    @Column(name = "oh_quantity")
    private Integer quantity;

    @Column(name = "oh_price")
    private Integer price;

    @JoinColumn(name = "oh_pay_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private PaymentInfoEntity paymentInfo;

    @JoinColumn(name = "oh_pi_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductInfoEntity product;

    @Builder
    public OrderHistoryEntity(String name, Integer quantity, Integer price,
            PaymentInfoEntity paymentInfo, ProductInfoEntity product) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.paymentInfo = paymentInfo;
        this.product = product;
    }
    
    
}
