package com.greenart.firstproject.yeongjang;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenart.firstproject.config.security.JwtProperties;
import com.greenart.firstproject.config.security.JwtUtil;
import com.greenart.firstproject.vo.review.ReviewCreateVO;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WebAppConfiguration
public class CreateReviewWithImagesTest {
    
    @Autowired private MockMvc mockMvc;
    @Autowired private JwtUtil jwtUtil;

    @Test
    void createReviewNoImages() throws JsonProcessingException, Exception {
        String jwt = JwtProperties.TOKEN_PREFIX + jwtUtil.create(28L);
        ReviewCreateVO review = ReviewCreateVO.builder()
        .content("test")
        .optionName("막걸리")
        .productSeq(2L)
        .grade(5.0).build();

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(
            multipart(HttpMethod.POST, "/api/reviews")
            .header(HttpHeaders.AUTHORIZATION, jwt)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .content(objectMapper.writeValueAsString(review))
        ).andExpect(status().isCreated())
        .andDo(print());
    }

    @Test
    void createReviewWithImages() {

    }

}
