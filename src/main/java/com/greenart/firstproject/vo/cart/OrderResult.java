package com.greenart.firstproject.vo.cart;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

// dto나 vo에 쓰기 좋다.
public record OrderResult(
    @Schema(description = "결제번호") Long seq,  
    @Schema(description = "메세지") String message, 
    @Schema(description = "최종결제금액") int totalPrice,
    @Schema(description = "결제한 옵션 리스트") List<String> optionList 
    ) {
}
