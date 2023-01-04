package com.greenart.firstproject.api;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.service.UserService;
import com.greenart.firstproject.vo.LoginVo;
import com.greenart.firstproject.vo.UpdateUserVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserAPIController {
    
    private final UserService userService;
    
    @PutMapping("/join")
    public ResponseEntity<Object> userJoin(@RequestBody UserEntity data){
        Map<String, Object> resultMap = userService.addUser(data);
        return new ResponseEntity<Object>(resultMap, (HttpStatus) resultMap.get("code"));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> userLogin(@RequestBody LoginVo data, HttpSession session){
        Map<String, Object> resulMap = userService.loginUser(data);
        session.setAttribute("loginUser", resulMap.get("loginUser"));
        return new ResponseEntity<Object>(resulMap, (HttpStatus)resulMap.get("code"));
    }

    @PatchMapping("/login/update")
    public ResponseEntity<Object> userUpdate(@RequestBody UpdateUserVo data, HttpSession session){
        Map<String, Object> resulMap = userService.modifyUser(data);
        session.setAttribute("loginUser", resulMap.get("loginUser"));
        return new ResponseEntity<>(resulMap, (HttpStatus)resulMap.get("code"));
    }
}

