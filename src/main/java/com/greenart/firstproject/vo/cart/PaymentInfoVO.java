package com.greenart.firstproject.vo.cart;

import java.time.LocalDateTime;
import java.util.List;

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
    @Schema(description = "결제정보 기본키")
    private Long seq;
    @Schema(description = "옵션이름")
    private List<String> optionName; // 주문내역에 대한 vo를 List로 받는다.
    @Schema(description = "상품총금액")
    private Integer originalPrice;
    @Schema(description = "최종결제금액")
    private Integer finalPrice;
    @Schema(description = "결제일")
    private LocalDateTime orderDt;
    @Schema(description = "취소여부")
    private Boolean canceled;

    // 기본키, 주문날짜, 옵션이름(orderhistory),상품금액총합, 최종결제금액, 취소여부
    public PaymentInfoVO(PaymentInfoEntity entity) {
        this.seq = entity.getSeq();
        // this.optionName = entity.getOrderHistories().stream().map(null) // 요런식으로 vo list를 받는다. join fetch
        this.originalPrice = entity.getOriginalPrice();
        this.finalPrice = entity.getFinalPrice();
        this.orderDt = entity.getOrderDt();
        this.canceled = entity.getCanceled();
    }
}