package com.greenart.firstproject.vo.cart;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderHistoryResponseBody<T> {
    
    private boolean status;
    private String message;
    private T data;
    
    public OrderHistoryResponseBody(boolean status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }

    
}
