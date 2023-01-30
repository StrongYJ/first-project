package com.greenart.firstproject.vo.cart;

import java.time.LocalDateTime;

import com.greenart.firstproject.entity.CartInfoEntity;
import com.greenart.firstproject.entity.OptionInfoEntity;
import com.greenart.firstproject.entity.OrderHistoryEntity;
import com.greenart.firstproject.entity.UserEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "주문내역")
public class OrderHistoryVO {
    @Schema(description = "주문날짜")
    private LocalDateTime orderDt;
    @Schema(description = "카트에서 긁어온 제품정보")
    private Long productSeq;
    @Schema(description = "카트에서 긁어온 옵션이름")
    private String optionName;
    @Schema(description = "수량")
    private Integer quantity;
    @Schema(description = "가격")
    private Integer price;
    @Schema(description = "취소여부")
    private Boolean canceled;
    @Schema(description = "배달상태")
    private Integer deliveryStatus;

    public OrderHistoryVO(OrderHistoryEntity entity) {
        this.productSeq = entity.getProduct().getSeq();
        // this.optionName = cartInfoVO.getOptionName();
        this.optionName = entity.getName();
        this.quantity = entity.getQuantity();
        this.price = entity.getPrice();
        this.canceled = entity.getCanceled();
        this.deliveryStatus = entity.getDeliveryStatus();
    }
    
}