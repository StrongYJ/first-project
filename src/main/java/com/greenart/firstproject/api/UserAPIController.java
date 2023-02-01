package com.greenart.firstproject.api;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.config.security.JwtUtil;
import com.greenart.firstproject.config.security.LoginUserSeq;
import com.greenart.firstproject.service.UserService;
import com.greenart.firstproject.vo.user.UserJoinVO;
import com.greenart.firstproject.vo.user.UserLoginVO;
import com.greenart.firstproject.vo.user.UserResponseVO;
import com.greenart.firstproject.vo.user.UserUpdateVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserAPIController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/checkEmail")
    public ResponseEntity<Object> check(String email){
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("duplicated", userService.isDuplicatedEmail(email));
        return ResponseEntity.ok().body(resultMap);
    }

    @PostMapping("/join")
    public ResponseEntity<Object> userJoin(@Validated @RequestBody UserJoinVO data){
        Map<String, Object> resultMap = new LinkedHashMap<>();
        UserResponseVO addUser = userService.addUser(data);
        if(addUser == null){
            resultMap.put("message", "중복된 이메일이거나 닉네임입니다.");
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
        resultMap.put("message", "회원이 등록되었습니다.");
        resultMap.put("data", addUser);
        return new ResponseEntity<Object>(resultMap, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> userLogin(@RequestBody UserLoginVO data){
        Map<String, Object> resultMap = userService.loginUser(data);
        return new ResponseEntity<Object>(resultMap, (HttpStatus)resultMap.get("code"));
    }

    @PutMapping("/login/update")
    public ResponseEntity<Object> userUpdate(@RequestBody UserUpdateVO data, Authentication authentication){
        Map<String, Object> resultMap = null;
        // Object loginUser = session.getAttribute(MySessionkeys.USER_LOGIN_KEY);
        // if(loginUser == null) {
        //     resultMap = new LinkedHashMap<String, Object>();
        //     resultMap.put("status", false);
        //     resultMap.put("message", "로그인 사용자 정보가 없습니다.");
        //     return new ResponseEntity<>(resultMap, HttpStatus.UNAUTHORIZED);
        // }
        Long seq = Long.parseLong(authentication.getName());
        resultMap = userService.modifyUser(data, seq);
        return new ResponseEntity<>(resultMap, (HttpStatus)resultMap.get("code"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> userDelete(@LoginUserSeq Long logined) {
        userService.deleteUser(logined);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("message", "탈퇴되었습니다.");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
