package com.greenart.firstproject.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.service.CartService;
import com.greenart.firstproject.vo.cart.CartInfoVO;
import com.greenart.firstproject.vo.cart.CartPlusMinusVO;
import com.greenart.firstproject.vo.cart.CartinfoResponseBody;
import com.greenart.firstproject.vo.cart.DiscountVO;
import com.greenart.firstproject.vo.cart.OrderResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "장바구니")
@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
@Slf4j
public class CartAPIController {
    private final CartService cartService;

    @Operation(summary = "장바구니 정보 받기")
    @ApiResponse(responseCode = "200", description = "장바구니 데이터")
    @ApiResponse(responseCode = "403", description = "로그인 되지 않은 유저가 접근시")
    @GetMapping("")
    public ResponseEntity<CartinfoResponseBody<List<CartInfoVO>>> getCartInfo(Authentication authentication) {
        // Map<String, Object> map = new LinkedHashMap<>();
        // map.put("data", cartService.getCartInfo(seq));
        Long userSeq = Long.parseLong(authentication.getName());
        return new ResponseEntity<>(
                new CartinfoResponseBody<>(true, null, cartService.getCartInfo(userSeq)), 
                HttpStatus.OK
            );
    }

    @Operation(summary = "장바구니 추가")
    @ApiResponse(responseCode = "200", description = "추가 성공 메세지")
    @ApiResponse(responseCode = "403", description = "로그인 되지 않은 유저가 접근시")
    @PostMapping("")
    public ResponseEntity<CartinfoResponseBody<Object>> postCartInfo(
            Authentication authentication, @RequestBody CartPlusMinusVO data) {
        Long userSeq = Long.parseLong(authentication.getName());
        cartService.cartAdd(userSeq, data);
        return new ResponseEntity<>(new CartinfoResponseBody<>(true, "추가되었습니다."), HttpStatus.OK);
    }

    @Operation(summary = "장바구니 수량 수정")
    @ApiResponse(responseCode = "200", description = "수정 성공 메세지", content = @Content)
    @ApiResponse(responseCode = "403", description = "로그인 되지 않은 유저가 접근시", content = @Content)
    @PutMapping("")
    public ResponseEntity<CartinfoResponseBody<Object>> putCartInfo(Authentication authentication, @RequestBody CartPlusMinusVO data) {
        Long userSeq = Long.parseLong(authentication.getName());
        cartService.cartSetQuantity(userSeq, data);
        return new ResponseEntity<>(new CartinfoResponseBody<>(true, "수정되었습니다."), HttpStatus.OK);
    }

    @Operation(summary = "장바구니 제품 삭제")
    @ApiResponse(responseCode = "200", description = "성공 메세지", content = @Content)
    @ApiResponse(responseCode = "403", description = "로그인 되지 않은 유저가 접근시", content = @Content)
    @DeleteMapping("")
    public ResponseEntity<CartinfoResponseBody<Object>> deleteCartInfo(
        Authentication authentication,
        @Parameter(description = "옵션의 seq번호", in = ParameterIn.QUERY)
        @RequestParam("seq") Long optionSeq) {
        Long userSeq = Long.parseLong(authentication.getName());
        cartService.cartDelete(userSeq, optionSeq);
        return new ResponseEntity<>(new CartinfoResponseBody<>(true, "삭제되었습니다."), HttpStatus.OK);
    }

    public ResponseEntity<OrderResult> order(Authentication authentication, @RequestBody DiscountVO discount) {
        Long userSeq = Long.parseLong(authentication.getName());
        OrderResult orderResult = cartService.order(userSeq, discount);
        return new ResponseEntity<>(orderResult, HttpStatus.OK);
    }
}
