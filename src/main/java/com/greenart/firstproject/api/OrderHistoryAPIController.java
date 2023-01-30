package com.greenart.firstproject.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.service.OrderHistoryService;
import com.greenart.firstproject.vo.cart.OrderHistoryResponseBody;
import com.greenart.firstproject.vo.cart.OrderHistoryVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "주문내역")
@RestController
@RequestMapping("/api/orderHistory")
@RequiredArgsConstructor
public class OrderHistoryAPIController {
    private final OrderHistoryService ohService;

    @Operation(summary = "결제내역 불러오기")
    @ApiResponse(responseCode = "200", description = "결제내역 데이터")
    @ApiResponse(responseCode = "401", description = "로그인 되지 않은 유저가 접근할때")
    @GetMapping("")
    public ResponseEntity<OrderHistoryResponseBody<List<OrderHistoryVO>>> getOrderHistory(Authentication authentication) {
        // Map<String, Object> map = new LinkedHashMap<String, Object>();
        // map.put("list", ohService.getOrderHistory(seq));
        Long seq = Long.parseLong(authentication.getName());
        return new ResponseEntity<>(
                new OrderHistoryResponseBody<>(true, null, ohService.getOrderHistory(seq)),
                HttpStatus.OK
        );
    }

    // GET주문취소내역
    @GetMapping("/canceled")
    public ResponseEntity<OrderHistoryResponseBody<List<OrderHistoryVO>>> getOrderCanceled(Authentication authentication) {
        Long seq = Long.parseLong(authentication.getName());

        return new ResponseEntity<>(
                new OrderHistoryResponseBody<>(true, null, ohService.getOrderCanceled(seq, false)),
                HttpStatus.OK
        );
    }
    // PATCH주문취소내역
    @PatchMapping("/canceled")
    public ResponseEntity<OrderHistoryResponseBody<List<OrderHistoryVO>>> patchOrderCanceled(Authentication authentication) {
        Long seq = Long.parseLong(authentication.getName());

        return new ResponseEntity<>(
                new OrderHistoryResponseBody<>(true, null, ohService.patchOrderCanceled(seq, false)),
                HttpStatus.CREATED
        );
    }


}
