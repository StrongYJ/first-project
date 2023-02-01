package com.greenart.firstproject.vo.cart;

import com.greenart.firstproject.entity.OrderHistoryEntity;

public record OrderedOption(String name, Integer quantity) {
    public OrderedOption(OrderHistoryEntity orderHistoryEntity) {
        this(orderHistoryEntity.getName(), orderHistoryEntity.getQuantity());
    }
}
