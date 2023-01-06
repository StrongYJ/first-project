package com.greenart.firstproject.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.greenart.firstproject.config.FilePath;
import com.greenart.firstproject.entity.AdminEntity;
import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.repository.AdminRepository;
import com.greenart.firstproject.repository.MarketInfoRepository;
import com.greenart.firstproject.repository.ProductInfoRepository;
import com.greenart.firstproject.vo.superadmin.AdminLoginVO;
import com.greenart.firstproject.vo.superadmin.AdminAddProductVO;
import com.greenart.firstproject.vo.superadmin.AdminMainProductInfoVO;
import com.greenart.firstproject.vo.superadmin.AdminOptionVO;
import com.greenart.firstproject.vo.superadmin.AdminUpdateProductVO;

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

    public Long getMarketSeq(AdminLoginVO data) {
        AdminEntity findByIdAndPwd = adminRepo.findByIdAndPwd(data.getId(), data.getPwd());
        return findByIdAndPwd.getMarketInfo().getSeq();
    }

    public Boolean isInvalidProductAdd(AdminAddProductVO data) {
        if(
            data.getBasicImg() == null || data.getDetailImg() == null || data.getLevel() == null ||
            data.getManufacturer() == null || data.getName() == null || data.getRaw() == null ||
            data.getSoda() == null || data.getSour() == null || data.getSubName() == null ||
            data.getSweetness() == null || data.getType() == null
        ) {
            return true;
        }
        if(
            (data.getLevel() < 0 || data.getLevel() > 100) ||
            (data.getSoda() < 0 || data.getSoda() > 3) ||
            (data.getSour() < 0 || data.getSour() > 3) ||
            (data.getSweetness() < 0 || data.getSweetness() > 3)
        ) {
            return true;
        }
        if(data.getName().isBlank() || data.getSubName().isBlank() || data.getManufacturer().isBlank()) {
            return true;
        }
        if(extractImageExt(data.getBasicImg()) == null || extractImageExt(data.getDetailImg()) == null) {
            return true;
        }
        return false;
    }

    public Boolean isInvalidProductUpdate(AdminUpdateProductVO data) {
        if(productRepo.findById(data.getSeq()).isPresent() == false) {
            return true;
        }
        if(
            data.getLevel() == null ||
            data.getManufacturer() == null || data.getName() == null || data.getRaw() == null ||
            data.getSoda() == null || data.getSour() == null || data.getSubName() == null ||
            data.getSweetness() == null || data.getType() == null
        ) {
            return true;
        }
        if(
            (data.getLevel() < 0 || data.getLevel() > 100) ||
            (data.getSoda() < 0 || data.getSoda() > 3) ||
            (data.getSour() < 0 || data.getSour() > 3) ||
            (data.getSweetness() < 0 || data.getSweetness() > 3)
        ) {
            return true;
        }
        if(data.getName().isBlank() || data.getSubName().isBlank() || data.getManufacturer().isBlank()) {
            return true;
        }
        if(!data.getBasicImg().isEmpty()) {
            if(extractImageExt(data.getBasicImg()) == null) {
                return true;
            }
        }
        if(!data.getDetailImg().isEmpty()) {
            if(extractImageExt(data.getDetailImg()) == null) {
                return true;
            }
        }

        return false;
    }
    
    public void productSave(AdminAddProductVO data) {
        String basicImgExt = extractImageExt(data.getBasicImg());
        String detailImgExt = extractImageExt(data.getDetailImg());
        String newBasicName = UUID.randomUUID().toString() + "." + basicImgExt;
        String newDetailName = UUID.randomUUID().toString() + "." + detailImgExt;
        try {
            data.getBasicImg().transferTo(Paths.get(FilePath.PRODUCT_BASIC_IMAGE).resolve(newBasicName));
            data.getDetailImg().transferTo(Paths.get(FilePath.PRODUCT_DETAIL_IMAGE).resolve(newDetailName));
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        productRepo.save(
            ProductInfoEntity.builder()
            .adminProductInfoVO(data)
            .basicImg(newBasicName)
            .detailImg(newDetailName)
            .build()
            );   
    }
    public void productUpdate(AdminUpdateProductVO data) {
        ProductInfoEntity product = productRepo.findById(data.getSeq()).get();
        if(!data.getBasicImg().isEmpty()) {
            try {
                data.getBasicImg().transferTo(Paths.get(FilePath.PRODUCT_BASIC_IMAGE).resolve(product.getImg()));
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!data.getDetailImg().isEmpty()) {
            try {
                data.getDetailImg().transferTo(Paths.get(FilePath.PRODUCT_DETAIL_IMAGE).resolve(product.getDetailImg()));
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        product.updateProductInfo(data);
        productRepo.save(product);
    }

    public String productDelete(Long seq) {
        Optional<ProductInfoEntity> findById = productRepo.findById(seq);
        if(findById.isPresent()) {
            ProductInfoEntity product = findById.get();
            productRepo.delete(product);
            return "제품이 성공적으로 삭제되었습니다.";
        }
        return "제품 정보를 삭제하지 못했습니다.";
    }

    public Page<AdminMainProductInfoVO> getMainProductsPage(Pageable pageable) {
        Page<AdminMainProductInfoVO> data = productRepo.findAll(pageable).map(AdminMainProductInfoVO::fromEntity);

        return data;
    }

    public AdminUpdateProductVO getProductBySeq(Long id) {
        Optional<ProductInfoEntity> findByIdProduct = productRepo.findById(id);
        if(findByIdProduct.isPresent()) {
            ProductInfoEntity productInfoEntity = findByIdProduct.get();
            return AdminUpdateProductVO.fromEntity(productInfoEntity);
        }
        return null;
    }

    public List<AdminOptionVO> getOptionsByProductSeq(Long seq) {
        return productRepo.findById(seq).get().getOptions().stream().map(AdminOptionVO::fromEntity).toList();
    }

    private static String extractImageExt(MultipartFile img) {
        if(img.getContentType().contains("image/jpeg")) {
            return "jpg";
        }
        return null;
    }
}
