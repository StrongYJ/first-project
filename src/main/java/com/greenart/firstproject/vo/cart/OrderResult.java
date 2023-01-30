package com.greenart.firstproject.vo.cart;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResult {
    private final String message;
    private final Integer totalPrice;
    private final List<String> orderData;
}
