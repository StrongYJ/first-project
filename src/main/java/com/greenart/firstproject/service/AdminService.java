package com.greenart.firstproject.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.greenart.firstproject.config.FilePath;
import com.greenart.firstproject.entity.AdminEntity;
import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.repository.AdminRepository;
import com.greenart.firstproject.repository.MarketInfoRepository;
import com.greenart.firstproject.repository.ProductInfoRepository;
import com.greenart.firstproject.vo.adminVOs.AdminLoginVO;
import com.greenart.firstproject.vo.adminVOs.ProductAddVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
    
    private final AdminRepository adminRepo;
    private final MarketInfoRepository marketInfoRepo;
    private final ProductInfoRepository productRepo;

    public Boolean loginCheckIdAndPwd(AdminLoginVO data) {
        if(adminRepo.countByidAndPwd(data.getId(), data.getPwd()) > 0) {
            return true;
        }
        return false;
    }

    public Boolean isSuper(AdminLoginVO data) {
        AdminEntity admin = adminRepo.findByIdAndPwd(data.getId(), data.getPwd());
        if(admin.getGrade() == 1) {
            return true;   
        }
        return false;
    }

    public Long getMarketId(AdminLoginVO data) {
        AdminEntity findByIdAndPwd = adminRepo.findByIdAndPwd(data.getId(), data.getPwd());
        return findByIdAndPwd.getMarketInfo().getSeq();
    }

    public Boolean productSave(ProductAddVO prod) {
        String basicImgExt = extractImageExt(prod.getBasicImg());
        String detailImgExt = extractImageExt(prod.getDetailImg());
        if(basicImgExt == null || detailImgExt == null) {
            return false;
        }
        String newBasicName = UUID.randomUUID().toString() + "." + basicImgExt;
        String newDetailName = UUID.randomUUID().toString() + "." + detailImgExt;
        try {
            prod.getBasicImg().transferTo(Paths.get(FilePath.PRODUCT_BASIC_IMAGE).resolve(newBasicName));
            prod.getDetailImg().transferTo(Paths.get(FilePath.PRODUCT_DETAIL_IMAGE).resolve(newDetailName));
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        productRepo.save(new ProductInfoEntity(prod, newBasicName, newDetailName));
        
        return true;
    }

    private static String extractImageExt(MultipartFile img) {
        if(img.getContentType().contains("image/jpeg")) {
            return "jpg";
        }
        else if(img.getContentType().contains("image/png")) {
            return "png";
        }
        return null;
    }
}
