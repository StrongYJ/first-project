package com.greenart.firstproject.service;

import java.util.List;

import org.springframework.stereotype.Service;


import com.greenart.firstproject.repository.PaymentInfoRepository;
import com.greenart.firstproject.repository.UserRepository;
import com.greenart.firstproject.vo.cart.PaymentInfoVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentInfoRepository payRepo;
    private final UserRepository userRepo;

    public List<PaymentInfoVO> getPaymentList(Long userSeq) {
        return payRepo.findByUserSeqWithFetch(userSeq).stream().map(PaymentInfoVO::new).toList();
    }

    
}
