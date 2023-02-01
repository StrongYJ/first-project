package com.greenart.firstproject.yeongjang;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.greenart.firstproject.config.security.PasswordEncoder;
import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.repository.ReviewRepository;
import com.greenart.firstproject.repository.UserRepository;
import com.greenart.firstproject.service.UserService;
import com.greenart.firstproject.vo.user.UserJoinVO;
import com.greenart.firstproject.vo.user.UserJoinWelcomeVO;
import com.greenart.firstproject.vo.user.UserLoginVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class MyTest {
    @Autowired private UserRepository uRepo;
    @Autowired private BCryptPasswordEncoder passwordEncoder;

    @Test
    @DisplayName("BCryptPasswordEncoder 회원로그인 테스트")
    void loginUser(){
        //given
        String eamil = "super12@service.com";
        String pwd = "qwerty1234@";
        
        //when
        UserEntity loginUser = uRepo.findById(25L).get();

        //then
        Assertions.assertThat(passwordEncoder.matches(pwd, loginUser.getPwd())).isTrue();
        
    }
}
