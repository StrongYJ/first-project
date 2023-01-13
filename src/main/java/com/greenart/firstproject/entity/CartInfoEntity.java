package com.greenart.firstproject.entity;

import com.greenart.firstproject.vo.cart.CartPlusMinusVO;

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
@Table(name = "cart_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartInfoEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ci_seq")
    private Long seq;
    
    @Column(name = "ci_stock")
    private Integer quantity;
    
    @JoinColumn(name = "ci_ui_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
    
    @JoinColumn(name = "ci_oi_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private OptionInfoEntity option;

    @Builder
    public CartInfoEntity(Integer quantity, UserEntity user, OptionInfoEntity option) {
        this.quantity = quantity;
        this.user = user;
        this.option = option;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public void addQuantity(Integer quantity) {
        this.quantity += quantity;
    }

}    

