package com.greenart.firstproject.yeongjun;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenart.firstproject.vo.user.UserJoinVO;
import com.greenart.firstproject.yeongjun.vo.UserJoinVOTest;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class JoinAgeRejectTest {
    
    @Autowired private MockMvc mockMvc;
    
    @Test
    void joinNotAdultTest() throws JsonProcessingException, Exception {
        UserJoinVO joinNotAdult = new UserJoinVO("fienfisw", "fsanfj@fjewnfj.com", "!dqwe234567", "fejfnbjakf", LocalDate.of(2005, 1, 1), "01025789878", "대구");
        ObjectMapper objectMapper = new ObjectMapper();
        
        mockMvc.perform(post("/api/users/join").contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(new UserJoinVOTest(joinNotAdult)))).andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    void joinAdultTest() throws JsonProcessingException, Exception {
        UserJoinVO joinAdult = new UserJoinVO("fienfisw", "fsanfj@fjewnfj.com", "!dqwe234567", "fejfnbjakf", LocalDate.of(2004, 12, 31), "01025789878", "대구");
        ObjectMapper objectMapper = new ObjectMapper();
        
        mockMvc.perform(post("/api/users/join").contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(new UserJoinVOTest(joinAdult)))).andExpect(status().isOk()).andDo(print());
    }

    
}
