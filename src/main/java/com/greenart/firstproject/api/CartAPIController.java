package com.greenart.firstproject.api;

import java.util.LinkedHashMap;
import java.util.List;
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

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartAPIController {
    private final CartService cartService;

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

    @PutMapping("")
    public ResponseEntity<Map<String, Object>> putCartInfo(HttpSession session, @RequestBody CartPlusMinusVO data) {
        Map<String, Object> map = new LinkedHashMap<>();
        Object loginUser = session.getAttribute(MySessionkeys.USER_LOGIN_KEY);
        if (loginUser == null || (loginUser instanceof UserEntity) == false) {
            map.put("messege", "No Session");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        cartService.cartSetStock((UserEntity)loginUser, data);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<Map<String, Object>> deleteCartInfo(HttpSession session, @RequestParam("seq") Long optionSeq) {
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
