package com.greenart.firstproject.vo.localadmin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateLocalMarketOptionStockVO {
    private Long stockSeq; // {id} marketStockEntity seq
    private String marketName;
    private String productName;    // ProductInfo pi_name 제품 명 seq
    private String optionName;  // OptionInfo oi_option 옵션 이름 seq
    private Integer optionPrice;     // OptionInfo oi_price 옵션 가격 seq
    private Integer stock;

    public void updateMarketOptionStockVO(LocalMarketOptionStockVO data) {
        this.stockSeq = data.getStockSeq();
        this.productName = data.getProductName();
        this.optionName = data.getOptionName();
        this.optionPrice = data.getOptionPrice();
        this.stock = data.getStock();
    } 

}
