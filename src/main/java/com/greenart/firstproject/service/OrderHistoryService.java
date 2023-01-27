package com.greenart.firstproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.greenart.firstproject.repository.OrderHistoryRepository;
import com.greenart.firstproject.repository.ProductInfoRepository;
import com.greenart.firstproject.repository.UserRepository;
import com.greenart.firstproject.vo.cart.OrderHistoryVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderHistoryService {
    private final OrderHistoryRepository ohRepo;
    private final ProductInfoRepository piRepo;
    private final UserRepository userRepo;

    public List<OrderHistoryVO> getOrderHistory(Long userSeq) {
        return ohRepo.findByUserSeqWithFetch(userSeq).stream().map(OrderHistoryVO::new).toList();
    }
}
