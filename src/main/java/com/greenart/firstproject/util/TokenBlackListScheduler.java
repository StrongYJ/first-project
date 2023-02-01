package com.greenart.firstproject.util;

import java.time.Instant;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.greenart.firstproject.repository.TokenBlackListRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class TokenBlackListScheduler {

    private final TokenBlackListRepository tokenBlackListRepo;

    @Transactional
    @Scheduled(fixedRate = 1000 * 60 * 60 * 2)
    void clearTokenBlackList() {
        tokenBlackListRepo.deleteAllbyExpireTimeBeforeNow(Instant.now().toEpochMilli());
    }
}
