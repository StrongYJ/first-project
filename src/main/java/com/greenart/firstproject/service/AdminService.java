package com.greenart.firstproject.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.greenart.firstproject.config.FilePath;
import com.greenart.firstproject.entity.AdminEntity;
import com.greenart.firstproject.entity.OptionInfoEntity;
import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.repository.AdminRepository;
import com.greenart.firstproject.repository.MarketInfoRepository;
import com.greenart.firstproject.repository.MarketStockRepository;
import com.greenart.firstproject.repository.OptionInfoRepository;
import com.greenart.firstproject.repository.ProductInfoRepository;
import com.greenart.firstproject.vo.superadmin.AdminLoginVO;
import com.greenart.firstproject.vo.superadmin.AdminAddProductVO;
import com.greenart.firstproject.vo.superadmin.AdminMainProductInfoVO;
import com.greenart.firstproject.vo.superadmin.AdminOptionVO;
import com.greenart.firstproject.vo.superadmin.AdminUpdateOptionVO;
import com.greenart.firstproject.vo.superadmin.AdminUpdateProductVO;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    
    private final AdminRepository adminRepo;
    private final ProductInfoRepository productRepo;
    private final OptionInfoRepository optionRepo;
    private final MarketStockRepository stockRepo;

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
        String newBasicName = "basic_" + UUID.randomUUID().toString() + "." + basicImgExt;
        String newDetailName = "detail_" + UUID.randomUUID().toString() + "." + detailImgExt;
        try {
            data.getBasicImg().transferTo(Paths.get(FilePath.PRODUCT_IMAGES).resolve(newBasicName));
            data.getDetailImg().transferTo(Paths.get(FilePath.PRODUCT_IMAGES).resolve(newDetailName));
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
                data.getBasicImg().transferTo(Paths.get(FilePath.PRODUCT_IMAGES).resolve(product.getImg()));
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!data.getDetailImg().isEmpty()) {
            try {
                data.getDetailImg().transferTo(Paths.get(FilePath.PRODUCT_IMAGES).resolve(product.getDetailImg()));
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
            new File(Paths.get(FilePath.PRODUCT_IMAGES).resolve(product.getImg()).toString()).delete();
            new File(Paths.get(FilePath.PRODUCT_IMAGES).resolve(product.getDetailImg()).toString()).delete();
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
        ProductInfoEntity productInfoEntity = productRepo.findById(seq).get();
        List<OptionInfoEntity> options = optionRepo.findByProduct(productInfoEntity);
        List<AdminOptionVO> result = options.stream().map(o -> 
            new AdminOptionVO(
                o.getSeq(), o.getOption(), o.getPrice(),
                stockRepo.findByOption(o).stream().collect(Collectors.summingInt(s -> s.getStock()))
                )
            ).toList();
        return result;
    }

    public String getProductName(Long seq) {
        Optional<ProductInfoEntity> findById = productRepo.findById(seq);
        if(findById.isPresent()) {
            return findById.get().getName();
        }
        return null;
    }

    public String addProductOption(Long seq, AdminOptionVO optionVO) {
        Optional<ProductInfoEntity> findById = productRepo.findById(seq);
        if(findById.isPresent()) {
            ProductInfoEntity productInfoEntity = findById.get();
            optionRepo.save(new OptionInfoEntity(optionVO, productInfoEntity));
            return "추가되었습니다.";
        }

        return "추가 실패";
    }

    public Long deleteProductOptionByOptionSeq(Long seq) {
        Optional<OptionInfoEntity> findById = optionRepo.findById(seq);
        if(findById.isPresent()) {
            OptionInfoEntity option = findById.get();
            ProductInfoEntity product = option.getProduct();
            if(product != null) {
                Long productSeq = product.getSeq();
                optionRepo.delete(option);
                return productSeq;
            }
        }
        return null;
    }

    public Long updateOption(AdminUpdateOptionVO data, Long optionSeq) {
        Optional<OptionInfoEntity> findById = optionRepo.findById(optionSeq);
        if(findById.isPresent()) {
            OptionInfoEntity option = findById.get();
            ProductInfoEntity product = option.getProduct();
            if(product == null) {
                return null;
            }
            option.modifyNameAndPrice(data.getName(), data.getPrice());
            optionRepo.save(option);
            return product.getSeq();
        }
        return null;
    }


    private static String extractImageExt(MultipartFile img) {
        if(img.getContentType().contains("image/jpeg")) {
            return "jpg";
        }
        return null;
    }


}
