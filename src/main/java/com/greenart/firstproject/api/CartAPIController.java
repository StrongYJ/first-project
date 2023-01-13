package com.greenart.firstproject.api;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.config.MySessionkeys;
import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.service.CartService;
import com.greenart.firstproject.vo.cart.CartInfoVO;
import com.greenart.firstproject.vo.cart.CartPlusMinusVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Tag(name = "장바구니", description = "장바구니 관련 api 로그인 세션이 있어야 조회가능합니다.")
@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartAPIController {
    private final CartService cartService;

    @Operation(summary = "장바구니 정보 받기", description = "로그인 세션을 이용해서 장바구니 정보를 받습니다.")
    @ApiResponse(responseCode = "200", description = "장바구니 데이터", content = @Content(schema = @Schema(implementation = CartInfoVO.class)))
    @ApiResponse(responseCode = "401", description = "로그인 되지 않은 유저가 접근시", content = @Content)
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getCartInfo(HttpSession session) {
        Map<String, Object> map = new LinkedHashMap<>();
        Object loginUser = session.getAttribute(MySessionkeys.USER_LOGIN_KEY);
        if(loginUser == null || (loginUser instanceof UserEntity) == false) {
            map.put("message", "No Session");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        map.put("data", cartService.getCartInfo((UserEntity) loginUser));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Operation(summary = "장바구니 추가", description = "로그인 세션을 이용해서 장바구니 추가합니다.")
    @ApiResponse(responseCode = "200", description = "추가 성공 메세지", content = @Content)
    @ApiResponse(responseCode = "401", description = "로그인 되지 않은 유저가 접근시", content = @Content)
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> postCartInfo(HttpSession session, @RequestBody CartPlusMinusVO data) {
        Map<String, Object> map = new LinkedHashMap<>();
        Object loginUser = session.getAttribute(MySessionkeys.USER_LOGIN_KEY);
        if(loginUser == null || (loginUser instanceof UserEntity) == false) {
            map.put("messege", "No Session");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        cartService.cartAdd((UserEntity)loginUser, data);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Operation(summary = "장바구니 수량 수정", description = "로그인 세션을 이용해서 장바구니 수량을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "수정 성공 메세지", content = @Content)
    @ApiResponse(responseCode = "401", description = "로그인 되지 않은 유저가 접근시", content = @Content)
    @PutMapping("")
    public ResponseEntity<Map<String, Object>> putCartInfo(HttpSession session, @RequestBody CartPlusMinusVO data) {
        Map<String, Object> map = new LinkedHashMap<>();
        Object loginUser = session.getAttribute(MySessionkeys.USER_LOGIN_KEY);
        if (loginUser == null || (loginUser instanceof UserEntity) == false) {
            map.put("messege", "No Session");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        cartService.cartSetQuantity((UserEntity)loginUser, data);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Operation(summary = "장바구니 제품 삭제", description = "로그인 세션과 옵션 seq번호를 이용해서 장바구니 정보를 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "성공 메세지", content = @Content)
    @ApiResponse(responseCode = "401", description = "로그인 되지 않은 유저가 접근시", content = @Content)
    @ApiResponse(responseCode = "400", description = "존재하지 않는 seq번호", content = @Content)
    @DeleteMapping("")
    public ResponseEntity<Map<String, Object>> deleteCartInfo(
        HttpSession session,
        @Parameter(description = "옵션의 seq번호", in = ParameterIn.QUERY)
        @RequestParam("seq")
        Long optionSeq
        ) {
        Map<String, Object> map = new LinkedHashMap<>();
        Object loginUser = session.getAttribute(MySessionkeys.USER_LOGIN_KEY);
        if (loginUser == null || (loginUser instanceof UserEntity) == false) {
            map.put("messege", "No Session");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        Boolean isDeleted = cartService.cartDelete((UserEntity) loginUser, optionSeq);
        if(isDeleted == false){
            map.put("message", "Wrong request");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        map.put("message", "Deleted");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
