package com.greenart.firstproject.vo.cart;

import java.time.LocalDateTime;

import com.greenart.firstproject.entity.CartInfoEntity;
import com.greenart.firstproject.entity.OptionInfoEntity;
import com.greenart.firstproject.entity.OrderHistoryEntity;
import com.greenart.firstproject.entity.PaymentInfoEntity;
import com.greenart.firstproject.entity.UserEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "결제정보")
public class PaymentInfoVO {
    @Schema(description = "상품총금액")
    private Integer originalPrice;
    @Schema(description = "최종결제금액")
    private Integer finalPrice;
    @Schema(description = "결제일")
    private LocalDateTime orderDt;

    public PaymentInfoVO(PaymentInfoEntity entity) {
        this.originalPrice = entity.getOriginalPrice();
        this.finalPrice = entity.getFinalPrice();
        this.orderDt = entity.getOrderDt();
    }
}