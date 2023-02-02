package com.greenart.firstproject.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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

import com.greenart.firstproject.vo.pay.PaymentCanceledVO;
import com.greenart.firstproject.vo.pay.PaymentDeliveryVO;

@Entity
@Table(name = "payment_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class PaymentInfoEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_seq")
    private Long seq;

    @Column(name = "pay_original")
    private Integer originalPrice;

    @Column(name = "pay_final")
    private Integer finalPrice;

    @CreatedDate
    @Column(name = "pay_order_dt", updatable = false)
    private LocalDateTime orderDt;
    
    @Column(name = "pay_delivery_status")
    private Integer deliveryStatus;

    @Column(name = "pay_canceled")
    private Boolean canceled;


    @JoinColumn(name = "pay_ui_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @OneToMany(mappedBy = "paymentInfo")
    private List<OrderHistoryEntity> orderHistories = new ArrayList<>();

    public PaymentInfoEntity(Integer originalPrice, Integer finalPrice, Integer deliveryStatus, 
            Boolean canceled ,UserEntity user) {
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.user = user;
        this.deliveryStatus = deliveryStatus;
        this.canceled = canceled;
    }

    public void setCanceledPayment(PaymentCanceledVO data) {
        this.canceled = data.getCanceled();
    }

    public void setDeliveryStatus(PaymentDeliveryVO data){
        this.deliveryStatus = data.getDeliveryStatus();
    }
    // public PaymentInfoEntity(Long seq, UserEntity user, OrderHistoryEntity orderHistories) {
    //     this.seq = seq;
    //     this.user = user;
    //     this.orderHistories = (List<OrderHistoryEntity>) orderHistories;
    // }

}
