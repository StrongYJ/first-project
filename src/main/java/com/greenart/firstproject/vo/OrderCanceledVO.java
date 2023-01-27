package com.greenart.firstproject.vo;

import com.greenart.firstproject.entity.OrderHistoryEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCanceledVO {
    // 주문날짜 옵션정보 수량 가격 배달상태 취소status
    private Long seq; // 주문정보키
    private String name; // 옵션이름
    private Integer quantity; //수량
    private Integer price; // 가격
    private Integer deliveryStatus; // 배달상태가 0이면 결제취소 가능
    private Boolean canceled; // 취소여부 false0이면 취소가능 !0 true 취소불가

    public static OrderCanceledVO fromEntity(OrderHistoryEntity entity) {
        return OrderCanceledVO.builder()
        .seq(entity.getSeq())
        .name(entity.getName())
        .quantity(entity.getQuantity())
        .price(entity.getPrice())
        .deliveryStatus(entity.getDeliveryStatus())
        .canceled(entity.getCanceled())
        .build();
    }
}
