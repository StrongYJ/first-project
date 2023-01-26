package com.greenart.firstproject.vo.cart;

import java.time.LocalDateTime;

import com.greenart.firstproject.entity.OrderHistoryEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "주문내역")
public class OrderHistoryVO {
    @Schema(description = "주문날짜")
    private LocalDateTime orderDt;
    @Schema(description = "카트에서 긁어온 제품정보")
    private Long productSeq;
    @Schema(description = "카트에서 긁어온 옵션정보")
    private Long optionSeq;
    @Schema(description = "수량")
    private Integer quantity;
    @Schema(description = "가격")
    private Integer price;
    @Schema(description = "취소여부")
    private Boolean canceled;

    public OrderHistoryVO(OrderHistoryEntity entity) {
        this.orderDt = entity.getOrderDt();
        this.productSeq = entity.getOption().getProduct().getSeq();
        this.optionSeq = entity.getOption().getSeq();
        this.quantity = entity.getQuantity();
        this.price = entity.getPrice();
        this.canceled = entity.getCanceled();
    }
}
// 기본키
// 주문날짜
// 카트에서 긁어온 제품정보
// 카트에서 긁어온 옵션정보
// 수량
// 가격
// 취소여부