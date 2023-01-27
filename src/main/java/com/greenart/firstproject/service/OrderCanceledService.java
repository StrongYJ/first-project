package com.greenart.firstproject.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenart.firstproject.entity.OrderHistoryEntity;
import com.greenart.firstproject.repository.OrderHistoryRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class OrderCanceledService {
    // 결제내역에서 취소누르면 나오는 내역
    // Controller는 Orderhistory꺼로
    // 서비스에서 캔슬을 true로 바꾸는 Patch API 와 내역List 출력되는 Get API 만들기
    private final OrderHistoryRepository orderhistoryRepo;
    
    // list 출력
    public List<OrderHistoryEntity> getOrderCanceledList(Long uiSeq) {
        
        // oh_canceled 가 true 인 것만 출력 oh_delivery_status 가 0인 것만 취소가능
        // oh_delivery_status 가 !0 면 취소 불가
        return orderhistoryRepo.findById(uiSeq).stream().map(null).toList;
    }
    
    // false를 true로 취소
    public List<OrderHistoryEntity> patchOrderCanceledInfo(Long uiSeq) {
        // oh_canceled 를 true로 바꾸는 기능 !0 면 취소 불가
        return;
    }

}