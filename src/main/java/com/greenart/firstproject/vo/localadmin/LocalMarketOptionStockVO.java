package com.greenart.firstproject.vo.localadmin;

import com.greenart.firstproject.entity.MarketStockEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocalMarketOptionStockVO {
    private Long seq; // {id} marketStockEntity seq
    private String address; // MarketInfo mi_adress 매장의 이름 seq
    private String name;    // ProductInfo pi_name 제품 명 seq
    private String option;  // OptionInfo oi_option 옵션 이름 seq
    private Integer price;     // OptionInfo oi_price 옵션 가격 seq
    private Integer stock;  // MarketStock ms_stock 옵션에 따른 재고 seq

    public static LocalMarketOptionStockVO fromLocalEntity(MarketStockEntity entity) {
        return LocalMarketOptionStockVO.builder()
        .seq(entity.getMarket().getSeq())
        .address(entity.getMarket().getAddress())
        .name(entity.getOption().getProduct().getName())
        .option(entity.getOption().getOption())
        .price(entity.getOption().getPrice())
        .stock(entity.getStock())
        .build();
    }
}
