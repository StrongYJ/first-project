package com.greenart.firstproject.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.config.security.LoginUserSeq;
import com.greenart.firstproject.service.CouponService;
import com.greenart.firstproject.vo.coupon.CouponInfoVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponAPIController {
    private final CouponService couponService;

        // 로그인을해서. 쿠폰페이지로 가면 내가 가지고 있는 쿠폰이 조회된다.
        // @GetMapping("")
        // public ResponseEntity<Map<String, Object>> getUserCoupon(HttpSession session){
        //     Map<String, Object> map = new LinkedHashMap<>();
        //     Object loginUser = session.getAttribute(MySessionkeys.USER_LOGIN_KEY);
        //     map.put("data", couponService.getUserCoupon((UserEntity)loginUser));
        //     return new ResponseEntity<>(map, HttpStatus.OK);
        // }

        // seq 번호로 받아서 출력
        @GetMapping("")
        public ResponseEntity<Map<String, Object>> getUserCoupon(@LoginUserSeq Long uiSeq){
            Map<String, Object> map = new LinkedHashMap<>();
            List<CouponInfoVO> coupon =  couponService.getUserCoupon(uiSeq);
            map.put("data", coupon);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
}
