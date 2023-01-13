package com.greenart.firstproject.api;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.config.MySessionkeys;
import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.entity.CouponInfoEntity;
import com.greenart.firstproject.repository.CouponInfoRefository;
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
        Map<String, Object> resultMap = null;
        Object ue = session.getAttribute(MySessionkeys.USER_LOGIN_KEY);
        if(ue == null) {
            resultMap = new LinkedHashMap<String, Object>();
            resultMap.put("status", false);
            resultMap.put("message", "로그인 사용자 정보가 없습니다.");
            return new ResponseEntity<>(resultMap, HttpStatus.UNAUTHORIZED);
        }
        resultMap = userService.modifyUser(data, (UserEntity)ue);
        return new ResponseEntity<>(resultMap, (HttpStatus)resultMap.get("code"));
    }

    @DeleteMapping("/login/delete")
    public ResponseEntity<Object> userDelete(HttpSession session){
        Object loginUser = session.getAttribute(MySessionkeys.USER_LOGIN_KEY);
        Map<String, Object> resultMap = userService.deleteUser((UserEntity) loginUser);
        session.invalidate();
        return new ResponseEntity<>(resultMap, (HttpStatus)resultMap.get("code"));
    }
}
