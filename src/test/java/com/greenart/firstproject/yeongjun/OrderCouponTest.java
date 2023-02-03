package com.greenart.firstproject.yeongjun;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenart.firstproject.config.security.JwtProperties;
import com.greenart.firstproject.config.security.JwtUtil;
import com.greenart.firstproject.repository.CouponInfoRefository;
import com.greenart.firstproject.vo.cart.CartPlusMinusVO;
import com.greenart.firstproject.vo.cart.DiscountVO;

import jakarta.persistence.EntityManager;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderCouponTest {
    
    @Autowired private EntityManager em;
    @Autowired private MockMvc mockMvc;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private CouponInfoRefository couponInfoRefository;

    @Test
    @Rollback(false)
    void order() throws JsonProcessingException, Exception {
        String jwt = JwtProperties.TOKEN_PREFIX + jwtUtil.create(31L);
        ObjectMapper objecMapper = new ObjectMapper();


        mockMvc.perform(post("/api/carts")
            .contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, jwt)
            .content(objecMapper.writeValueAsString(new CartPlusMinusVO(1L, 1)))
        ).andExpect(status().isOk());

        mockMvc.perform(post("/api/carts/order").header(HttpHeaders.AUTHORIZATION, jwt)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objecMapper.writeValueAsString(new DiscountVO(118L, null)))
        ).andExpect(status().isOk());

        // Assertions.assertThat(couponInfoRefository.findById(118L).get().getCouStatus()).isEqualTo(2);
        Assertions.assertThat(couponInfoRefository.existsById(118L)).isFalse();

    }
}
