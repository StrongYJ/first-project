package com.greenart.firstproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.greenart.firstproject.entity.PaymentInfoEntity;
import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.repository.OrderHistoryRepository;
import com.greenart.firstproject.repository.PaymentInfoRepository;
import com.greenart.firstproject.repository.UserRepository;
import com.greenart.firstproject.vo.cart.PaymentInfoVO;
import com.greenart.firstproject.vo.cart.PaymentOptionVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentInfoRepository payRepo;
    private final UserRepository userRepo;
    private final OrderHistoryRepository ohRepo;
    private PaymentInfoEntity payEntity;
    private UserEntity userEntity;


    public List<PaymentInfoVO> getPaymentList(Long userSeq) {
        return payRepo.findByUserSeqWithFetch(userSeq).stream().map(PaymentInfoVO::new).toList();
    }

        // 기본키, 주문날짜, 옵션이름(orderhistory),상품금액총합, 최종결제금액, 취소여부
        private PaymentInfoVO PaymentInfoVO(PaymentInfoEntity entity) {
            return PaymentInfoVO.builder()
            .seq(entity.getSeq())
            .orderDt(entity.getOrderDt())
            .paymentOption(entity.getOrderHistories().stream().map(PaymentOptionVO::new).toList())
            .originalPrice(entity.getOriginalPrice())
            .finalPrice(entity.getFinalPrice())
            .deliveryStatus(entity.getDeliveryStatus())
            .build();
        }
}
