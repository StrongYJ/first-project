package com.greenart.firstproject.api;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.config.security.LoginUserSeq;

@RestController
public class TestAPIController {
    
    @GetMapping("/test")
    public String test(Authentication authentication) {
        Long seq = Long.parseLong(authentication.getName());
        return "seq: " + seq;
    }
    
    @GetMapping("/test2")
    public String test(@LoginUserSeq Long seq) {
        return "seq: " + seq;
    } 
}
