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
@Table(name = "market_stock")
@Getter @Setter
public class MarketStockEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ms_seq")
    private Long seq;
    
    @Column(name = "ms_stock")
    private Integer stock;
    
    @JoinColumn(name = "ms_mi_seq")
    @ManyToOne(fetch = FetchType.LAZY) // 일대다연결
    private MarketInfoEntity market;
    
    @JoinColumn(name = "ms_oi_seq")
    @ManyToOne(fetch = FetchType.LAZY) // 일대다연결
    private OptionInfoEntity option;

    @Override
    public String toString() {
        return ""+stock;
    }
}
