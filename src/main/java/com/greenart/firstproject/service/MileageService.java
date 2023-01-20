package com.greenart.firstproject.service;

import org.springframework.stereotype.Service;

import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.repository.MilegePointRepository;
import com.greenart.firstproject.vo.MileageInfoVO;

import java.util.List;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class MileageService {
    private final MilegePointRepository milegeRepo;

    public List<MileageInfoVO> getUserMileage(UserEntity loginUser) {
        return milegeRepo.findByUser(loginUser).stream().map(MileageInfoVO::fromEntity).toList();
    }
}
