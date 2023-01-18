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
import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.repository.AdminRepository;
import com.greenart.firstproject.repository.MarketInfoRepository;
import com.greenart.firstproject.repository.MarketStockRepository;
import com.greenart.firstproject.repository.OptionInfoRepository;
import com.greenart.firstproject.repository.ProductInfoRepository;
import com.greenart.firstproject.repository.UserRepository;
import com.greenart.firstproject.vo.superadmin.AdminLoginVO;
import com.greenart.firstproject.vo.superadmin.AdminAddProductVO;
import com.greenart.firstproject.vo.superadmin.AdminMainProductInfoVO;
import com.greenart.firstproject.vo.superadmin.AdminOptionVO;
import com.greenart.firstproject.vo.superadmin.AdminUpdateOptionVO;
import com.greenart.firstproject.vo.superadmin.AdminUpdateProductVO;
import com.greenart.firstproject.vo.superadmin.AdminUserVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AdminService {
    
    private final AdminRepository adminRepo;
    private final ProductInfoRepository productRepo;
    private final OptionInfoRepository optionRepo;
    private final MarketStockRepository stockRepo;
    private final UserRepository userRepo;

    public AdminEntity login(AdminLoginVO data) {
        return adminRepo.findByIdAndPwd(data.getId(), data.getPwd()).orElse(null);
    }

    public Boolean isInvalidProductAdd(AdminAddProductVO data) {
        if(extractImageExt(data.getBasicImg()) == null || extractImageExt(data.getDetailImg()) == null) {
            return true;
        }
        return false;
    }

    public Boolean invalidFileFormat(MultipartFile ...files) {
        for(MultipartFile file : files) {
            if(file.isEmpty() == false && extractImageExt(file) == null) {
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

    @Transactional
    public void productUpdate(AdminUpdateProductVO data) {
        ProductInfoEntity product = productRepo.findById(data.getSeq()).orElseThrow();
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
        return AdminUpdateProductVO.fromEntity(productRepo.findById(id).orElseThrow());
    }

    public List<AdminOptionVO> getOptionsByProductSeq(Long seq) {
        List<OptionInfoEntity> options = optionRepo.findByProduct(productRepo.findById(seq).orElseThrow());
        List<AdminOptionVO> result = options.stream().map(o -> 
            new AdminOptionVO(
                o.getSeq(), o.getOption(), o.getPrice(),
                stockRepo.findByOption(o).stream().collect(Collectors.summingInt(s -> s.getStock()))
                )
            ).toList();
        return result;
    }

    public String getProductName(Long seq) {
       return productRepo.findById(seq).orElseThrow().getName();
    }

    public String addProductOption(Long seq, AdminOptionVO optionVO) {
        optionRepo.save(new OptionInfoEntity(optionVO, productRepo.findById(seq).orElseThrow()));
        return "추가되었습니다.";
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

    @Transactional
    public Long updateOption(AdminUpdateOptionVO data, Long optionSeq) {
        Optional<OptionInfoEntity> findById = optionRepo.findById(optionSeq);
        if(findById.isPresent()) {
            OptionInfoEntity option = findById.get();
            ProductInfoEntity product = option.getProduct();
            if(product == null) {
                return null;
            }
            option.modifyNameAndPrice(data.getName(), data.getPrice());
            return product.getSeq();
        }
        return null;
    }

    public Page<AdminUserVO> getAllUsers(Pageable pageable) {
        return userRepo.findAdminUserVOAll(pageable);
    }

    public AdminUserVO getUserInfoBySeq(Long seq) {
        return userRepo.findAdminUserVOBySeq(seq);
    }

    private static String extractImageExt(MultipartFile img) {
        if(img.getContentType().contains("image/jpeg")) {
            return "jpg";
        }
        return null;
    }

    @Transactional
    public void changeUserStatus(Long seq, Integer status) {
        Optional<UserEntity> findById = userRepo.findById(seq);
        if(findById.isPresent()) {
            UserEntity userEntity = findById.get();
            userEntity.changeStatus(status);
            userRepo.save(userEntity);
        }
    }
}
