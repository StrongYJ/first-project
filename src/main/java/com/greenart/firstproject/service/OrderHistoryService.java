package com.greenart.firstproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.greenart.firstproject.entity.OrderHistoryEntity;
import com.greenart.firstproject.repository.OrderHistoryRepository;
import com.greenart.firstproject.repository.PaymentInfoRepository;
import com.greenart.firstproject.repository.ProductInfoRepository;
import com.greenart.firstproject.repository.UserRepository;
import com.greenart.firstproject.vo.cart.OrderHistoryVO;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderHistoryService {
    private final OrderHistoryRepository ohRepo;
    private final ProductInfoRepository piRepo;
    private final UserRepository userRepo;
    private final PaymentInfoRepository payRepo;

    // public List<OrderHistoryVO> getOrderHistory(Long userSeq) {
    //     return payRepo.findByUserSeqWithFetch(userSeq).stream().map(OrderHistoryVO::new).toList();
    // }

    // // 주문취소내역서비스 patch
    // public List<OrderHistoryVO> patchOrderCanceled(Long userSeq) {
    //     return ohRepo.findByUserSeqWithFetch(userSeq).stream().map(OrderHistoryVO::new).toList();

    // }
    // // public List<OrderHistoryVO> getOrderHistory(Long userSeq) {
    // //     return ohRepo.findByUserSeqWithFetch(userSeq).stream().map(OrderHistoryVO::new).toList();
    // // }
    
    // 주문취소내역서비스 get
}
