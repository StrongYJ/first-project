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

import com.greenart.firstproject.service.UserService;
import com.greenart.firstproject.vo.UserJoinVO;
import com.greenart.firstproject.vo.UserLoginVO;
import com.greenart.firstproject.vo.UserUpdateVO;

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
        session.setAttribute("loginUser", resultMap.get("loginUser"));
        return new ResponseEntity<Object>(resultMap, (HttpStatus)resultMap.get("code"));
    }

    @PutMapping("/login/update")
    public ResponseEntity<Object> userUpdate(@RequestBody UserUpdateVO data, HttpSession session){
        Map<String, Object> resultMap = userService.modifyUser(data);
        session.getAttribute("loginUser");
        if(resultMap.get("loginUser") == null){
            session.invalidate();
        }
        return new ResponseEntity<>(resultMap, (HttpStatus)resultMap.get("code"));
    }

    @DeleteMapping("/login/delete")
    public ResponseEntity<Object> userDelete(@RequestBody UserUpdateVO data, HttpSession session){
        Map<String, Object> resultMap = userService.deleteUser(data);
        session.getAttribute("loginUser");
        session.invalidate();
        return new ResponseEntity<>(resultMap, (HttpStatus)resultMap.get("code"));
    }

    
}
