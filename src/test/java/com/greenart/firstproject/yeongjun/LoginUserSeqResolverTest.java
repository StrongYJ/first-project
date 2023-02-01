package com.greenart.firstproject.yeongjun;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import com.greenart.firstproject.config.security.JwtProperties;
import com.greenart.firstproject.config.security.JwtUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginUserSeqResolverTest {
    @Autowired private JwtUtil jwtUtil;
    @Autowired private MockMvc mockMvc;


    @Test
    void resolverTest() throws Exception {
        mockMvc.perform(get("/test").header(HttpHeaders.AUTHORIZATION,JwtProperties.TOKEN_PREFIX + jwtUtil.create(25L)))
        .andExpect(content().string("seq: " + 25L))
        .andDo(print());
    }
    
    @Test
    void resolverTest2() throws Exception {
        mockMvc.perform(get("/test2").header(HttpHeaders.AUTHORIZATION,JwtProperties.TOKEN_PREFIX + jwtUtil.create(25L)))
        .andDo(print());
    }
}
