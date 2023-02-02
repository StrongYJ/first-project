package com.greenart.firstproject.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenart.firstproject.entity.PaymentInfoEntity;
import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.repository.OrderHistoryRepository;
import com.greenart.firstproject.repository.PaymentInfoRepository;
import com.greenart.firstproject.repository.UserRepository;
import com.greenart.firstproject.vo.pay.PaymentInfoVO;
import com.greenart.firstproject.vo.pay.PaymentOptionVO;
import com.greenart.firstproject.vo.pay.PaymentCanceledVO;
import com.greenart.firstproject.vo.pay.PaymentDeliveryVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentInfoRepository payRepo;
    private final UserRepository userRepo;
    private final OrderHistoryRepository ohRepo;
    private PaymentInfoEntity payEntity;
    private UserEntity userEntity;


    // public List<PaymentInfoVO> getPaymentList(Long userSeq) {
    //     return payRepo.findByUserSeqWithFetch(userSeq).stream().map(PaymentInfoVO::new).toList();
    // }
    public List<PaymentInfoVO> getPaymentList(Long uiSeq) {
        return payRepo.findByFetchByUserSeq(uiSeq).stream().map(PaymentInfoVO::new).toList();
    }

    // 성공한거
    // @Transactional
    public PaymentCanceledVO calceledPayment(Long paySeq, PaymentCanceledVO data) {
        PaymentInfoEntity canceledPayment = payRepo.findById(paySeq).orElseThrow();
        canceledPayment.setCanceledPayment(data);
        payRepo.save(canceledPayment);
        return data;
    }

    // 배달상태값에 따라 취소
    // public Map<String, Object> deliveryPayment(Long paySeq, PaymentDeliveryVO data) {
    //     Map<String, Object> map = new LinkedHashMap<String, Object>();
    //     PaymentInfoEntity entity = payRepo.findById(paySeq).orElseThrow();
    //     if(entity.getDeliveryStatus() == 0) {
    //         map.put("message", "주문 취소 완료");
    //         map.put("data", entity);
    //     }
    //     else if(entity.getDeliveryStatus() == 1) {
    //         map.put("message", "주문확인 되었으므로 취소가 불가능합니다");
    //         map.put("data", entity);
    //     }
    //     else if(entity.getDeliveryStatus() == 2) {
    //         map.put("message", "배달중에는 취소가 불가능합니다");
    //         map.put("data", entity);
    //     }
    //     else if(entity.getDeliveryStatus() == 3) {
    //         map.put("message", "배달완료인 상태는 취소가 불가능합니다");
    //         map.put("data", entity);
    //     }
    //     else {
    //         map.put("message", "수령확인 되었으므로 취소가 불가능합니다");
    //         map.put("data", entity);
    //     }
    //     return map;
    // }

    // public PaymentDeliveryVO deliveryPayment(Long paySeq, PaymentDeliveryVO data) {
    //     PaymentInfoEntity deliveryPayment = payRepo.findById(paySeq).orElseThrow();
    //     payRepo.findByFetchByUserSeqAndDeliveryStatus(paySeq, deliveryPayment.getDeliveryStatus());
    //     return data;
    // }

        // 기본키, 주문날짜, 옵션이름(orderhistory),상품금액총합, 최종결제금액, 취소여부
        public PaymentInfoVO getPaymentInfoVO(PaymentInfoEntity entity) {
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
