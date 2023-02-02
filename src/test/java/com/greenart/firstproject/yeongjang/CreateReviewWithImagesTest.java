package com.greenart.firstproject.yeongjang;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
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
        MockMultipartFile body = new MockMultipartFile(
            "body", null, 
            MediaType.APPLICATION_JSON.toString(), 
            objectMapper.writeValueAsString(review).getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(
            multipart(HttpMethod.POST, "/api/reviews")
                .file(body)
                .header(HttpHeaders.AUTHORIZATION, jwt)
        )
        .andExpect(status().isCreated())
        .andDo(print());
    }

    @Test
    void createReviewWithImages() throws Exception {
        final String filePath = "/testImages/"; // 파일경로
        
        // Mock파일생성
        MockMultipartFile image1 = new MockMultipartFile("file", "review1.jpg", "jpg", new FileInputStream(filePath + "review1.jpg"));
        MockMultipartFile image2 = new MockMultipartFile("file", "review2.jpg", "jpg", new FileInputStream(filePath + "review2.jpg"));
    
        String jwt = JwtProperties.TOKEN_PREFIX + jwtUtil.create(28L);
        ReviewCreateVO review = ReviewCreateVO.builder()
                .content("test")
                .optionName("막걸리")
                .productSeq(2L)
                .grade(5.0)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        MockMultipartFile body = new MockMultipartFile(
                "body", null,
                MediaType.APPLICATION_JSON.toString(),
                objectMapper.writeValueAsString(review).getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(
            multipart(HttpMethod.POST, "/api/reviews")
                .file(image1)
                .file(image2)
                .file(body)
                .header(HttpHeaders.AUTHORIZATION, jwt)
            )
        .andExpect(status().isCreated())
        .andDo(print());
    }

}
