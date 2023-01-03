package com.greenart.firstproject.service;

import org.springframework.stereotype.Service;

import com.greenart.firstproject.entity.AdminEntity;
import com.greenart.firstproject.repository.AdminRepository;
import com.greenart.firstproject.repository.MarketInfoRepository;
import com.greenart.firstproject.vo.AdminVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
    
    private final AdminRepository adminRepo;
    private final MarketInfoRepository marketInfoRepo;

    public Boolean loginCheckIdAndPwd(AdminVO data) {
        if(adminRepo.countByidAndPwd(data.getId(), data.getPwd()) > 0) {
            return true;
        }
        return false;
    }

    public Boolean isSuper(AdminVO data) {
        AdminEntity admin = adminRepo.findByIdAndPwd(data.getId(), data.getPwd());
        if(admin.getGrade() == 1) {
            return true;   
        }
        return false;
    }

    public Long localId(AdminVO data) {
        AdminEntity findByIdAndPwd = adminRepo.findByIdAndPwd(data.getId(), data.getPwd());
        return findByIdAndPwd.getMarketInfo().getSeq();
    }
}
