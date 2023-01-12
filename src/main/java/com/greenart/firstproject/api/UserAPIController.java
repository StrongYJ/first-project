package com.greenart.firstproject.api;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.config.MySessionkeys;
import com.greenart.firstproject.service.UserService;
import com.greenart.firstproject.vo.user.UserJoinVO;
import com.greenart.firstproject.vo.user.UserLoginVO;
import com.greenart.firstproject.vo.user.UserUpdateVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserAPIController {
    
    private final UserService userService;
    
    @PutMapping("/join")
    public ResponseEntity<Object> userJoin(@RequestBody UserJoinVO data){
        Map<String, Object> resultMap = userService.addUser(data);
        return new ResponseEntity<Object>(resultMap, (HttpStatus) resultMap.get("code"));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> userLogin(@RequestBody UserLoginVO data, HttpSession session){
        Map<String, Object> resultMap = userService.loginUser(data);
        session.setAttribute(MySessionkeys.USER_LOGIN_KEY, resultMap.get(MySessionkeys.USER_LOGIN_KEY));
        return new ResponseEntity<Object>(resultMap, (HttpStatus)resultMap.get("code"));
    }

    @PutMapping("/login/update")
    public ResponseEntity<Object> userUpdate(@RequestBody UserUpdateVO data, HttpSession session){
        Map<String, Object> resultMap = userService.modifyUser(data);
        session.getAttribute(MySessionkeys.USER_LOGIN_KEY);
        if(resultMap.get(MySessionkeys.USER_LOGIN_KEY) == null){
            session.invalidate();
        }
        return new ResponseEntity<>(resultMap, (HttpStatus)resultMap.get("code"));
    }

    @DeleteMapping("/login/delete")
    public ResponseEntity<Object> userDelete(@RequestBody UserUpdateVO data, HttpSession session){
        Map<String, Object> resultMap = userService.deleteUser(data);
        session.getAttribute(MySessionkeys.USER_LOGIN_KEY);
        session.invalidate();
        return new ResponseEntity<>(resultMap, (HttpStatus)resultMap.get("code"));
    }

    // 로그인을해서. 쿠폰페이지로 가면 내가 가지고 있는 쿠폰이 조회된다.
    @GetMapping("/coupons")
    public ResponseEntity<Object> userCoupon(@RequestBody UserLoginVO data, HttpSession session) {
        Map<String, Object> resultMap = userService.loginUser(data);
        
        session.setAttribute("loginUser", resultMap.get("loginUser"));
        return new ResponseEntity<Object>(resultMap, (HttpStatus)resultMap.get("code"));
    }
    
}
