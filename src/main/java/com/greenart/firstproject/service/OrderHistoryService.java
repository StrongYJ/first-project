package com.greenart.firstproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.greenart.firstproject.entity.OrderHistoryEntity;
import com.greenart.firstproject.repository.OrderHistoryRepository;
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

    public List<OrderHistoryVO> getOrderHistory(Long userSeq) {
        return ohRepo.findByUserSeqWithFetch(userSeq).stream().map(OrderHistoryVO::new).toList();
    }
    
    // 주문취소내역서비스 get
    public List<OrderHistoryVO> getOrderCanceled(Long userSeq, boolean canceled) {
        return ohRepo.findByUserSeqCanceledWithFetch(userSeq,canceled).stream().map(OrderHistoryVO::new).toList();
    }

    // 주문취소내역서비스 patch
    public List<OrderHistoryVO> patchOrderCanceled(Long userSeq, boolean canceled) {
        return ohRepo.findByUserSeqCanceledWithFetch(userSeq,canceled).stream().map(OrderHistoryVO::new).toList();

    }
    
    private OrderHistoryVO OrderCanceledHistoryVO(OrderHistoryEntity entity) {
        return OrderHistoryVO.builder()
        .orderDt(entity.getOrderDt())
        .productSeq(entity.getProduct().getSeq())
        .optionName(entity.getName())
        .quantity(entity.getQuantity())
        .price(entity.getPrice())
        .canceled(entity.getCanceled())
        .build();
    }
}
