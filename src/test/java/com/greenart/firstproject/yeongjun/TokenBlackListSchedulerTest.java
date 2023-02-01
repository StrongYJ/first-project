package com.greenart.firstproject.yeongjun;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.greenart.firstproject.repository.TokenBlackListRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class TokenBlackListSchedulerTest {

    @Autowired private TokenBlackListRepository tokenBlackListRepo;

    @Test
    void test() {
        long now = Instant.now().toEpochMilli();
        log.info("now = {}", now);
        tokenBlackListRepo.deleteAllbyExpireTimeBeforeNow(Instant.now().toEpochMilli());
    }
}
