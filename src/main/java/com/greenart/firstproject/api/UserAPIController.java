package com.greenart.firstproject.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserAPIController {
    @Autowired
    UserService user;
    
    @PutMapping("/join")
    public ResponseEntity<Object> userJoin(@RequestBody UserEntity data){
        Map<String, Object> resultMap = user.addUser(data);
        return new ResponseEntity<Object>(resultMap, (HttpStatus) resultMap.get("code"));
    }
}
