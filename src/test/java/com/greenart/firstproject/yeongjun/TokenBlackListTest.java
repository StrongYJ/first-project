package com.greenart.firstproject.yeongjun;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import com.greenart.firstproject.config.security.JwtProperties;
import com.greenart.firstproject.config.security.JwtUtil;
import com.greenart.firstproject.service.UserService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class TokenBlackListTest {
    @Autowired private UserService userService;
    @Autowired private MockMvc mockMvc;
    @Autowired private JwtUtil jwtUtil;

    @Test
    void logoutTest() throws Exception {
        long seq = 1;
        String jwt = JwtProperties.TOKEN_PREFIX + jwtUtil.create(seq);
        log.info("jwt = {}", jwt);  

        mockMvc.perform(get("/test2").header(HttpHeaders.AUTHORIZATION, jwt))
        .andExpect(content().string("seq: " + seq))
        .andDo(print());

        mockMvc.perform(post("/api/users/logout").header(HttpHeaders.AUTHORIZATION, jwt))
        .andDo(print());

        mockMvc.perform(get("/test2").header(HttpHeaders.AUTHORIZATION, jwt))
                .andExpect(status().isForbidden())
                .andDo(print());
    }
}
