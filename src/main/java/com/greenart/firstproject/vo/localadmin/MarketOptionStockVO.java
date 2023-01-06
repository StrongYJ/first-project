package com.greenart.firstproject.vo.localadmin;

import com.greenart.firstproject.entity.MarketInfoEntity;
import com.greenart.firstproject.entity.MarketStockEntity;
import com.greenart.firstproject.entity.OptionInfoEntity;
import com.greenart.firstproject.entity.ProductInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketOptionStockVO {
    private String address; // MarketInfo mi_adress 매장의 이름 seq
    private String name;    // ProductInfo pi_name 제품 명 seq
    private String option;  // OptionInfo oi_option 옵션 이름 seq
    private Integer price;     // OptionInfo oi_price 옵션 가격 seq
    private Integer stock;  // MarketStock ms_stock 옵션에 따른 재고 seq

    public static MarketOptionStockVO fromEntity(MarketStockEntity entity) {
        return MarketOptionStockVO.builder()
            .address(entity.getMarket().getAddress())
            .name(entity.getOption().getProduct().getName())
            .option(entity.getOption().getOption())
            .price(entity.getOption().getPrice())
            .stock(entity.getStock())
            .build();
    }
    
}
