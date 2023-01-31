package com.greenart.firstproject.vo.cart;

// dto나 vo에 쓰기 좋다.
public record OrderResult(Long seq, String message, int totalPrice) {
    public OrderResult(Long seq) {
        this(seq, null, 0);
    }
}
