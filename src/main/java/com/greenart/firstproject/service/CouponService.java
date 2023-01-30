package com.greenart.firstproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.repository.CouponInfoRefository;
import com.greenart.firstproject.repository.UserRepository;
import com.greenart.firstproject.vo.coupon.CouponInfoVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponInfoRefository couponRepo;
    
    public List<CouponInfoVO> getUserCoupon(UserEntity loginUser) {
        return couponRepo.findByUser(loginUser).stream().map(CouponInfoVO::fromEntity).toList();
    }
    
    //seq로 받기
    public List<CouponInfoVO> getUserCoupon(Long uiSeq) {
        return couponRepo.findByFetchByUserSeq(uiSeq).stream().map(CouponInfoVO::new).toList();
    }
}
