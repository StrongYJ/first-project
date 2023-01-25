package com.greenart.firstproject.vo.localadmin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocalMarketOptionStockVO {
    private Long stockSeq; // {id} marketStockEntity seq
    private String marketName;
    private String productName;    // ProductInfo pi_name 제품 명 seq
    private String optionName;  // OptionInfo oi_option 옵션 이름 seq
    private Integer optionPrice;     // OptionInfo oi_price 옵션 가격 seq
    private Integer stock;
      // MarketStock ms_stock 옵션에 따른 재고 seq


    public LocalMarketOptionStockVO(Long stockSeq, String productName, String optionName, Integer optionPrice,
            Integer stock) {
        this.stockSeq = stockSeq;
        this.productName = productName;
        this.optionName = optionName;
        this.optionPrice = optionPrice;
        this.stock = stock;
    } 
    // public void updateMarketOptionStockInfo(LocalMarketOptionStockVO data) {
    //     this.stockSeq = data.getStockSeq();
    //     this.productName = data.getProductName();
    //     this.optionName = data.getOptionName();
    //     this.optionPrice = data.getOptionPrice();
    //     this.stock = data.getStock();
    // } 
}
