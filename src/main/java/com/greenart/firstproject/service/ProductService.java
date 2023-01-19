package com.greenart.firstproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.entity.enums.AlcoholType;
import com.greenart.firstproject.repository.OptionInfoRepository;
import com.greenart.firstproject.repository.ProductInfoRepository;
import com.greenart.firstproject.vo.product.OptionVO;
import com.greenart.firstproject.vo.product.ProductMainVO;
import com.greenart.firstproject.vo.product.ProductSearchCond;
import com.greenart.firstproject.vo.product.ProductVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductInfoRepository piRepo;
    private final OptionInfoRepository oiRepo;

    public Page<ProductInfoEntity> findAllProducts(Pageable pageable) {
        return piRepo.findAll(pageable);
    }

    public Page<ProductInfoEntity> findByType(AlcoholType type, Pageable pageable) {
        return piRepo.findByType(type, pageable);
    }

    public ProductInfoEntity findProductDetail(Long seq) {
        return piRepo.findById(seq).orElseThrow();
    }
    
    public List<ProductVO> searchProducts(String keyword) {
        List<ProductInfoEntity> productInfoEntity = piRepo.findByNameContaining(keyword);
        List<ProductVO> productVOlList = new ArrayList<>();
        
        if(productInfoEntity.isEmpty()) return productVOlList;

        for(ProductInfoEntity productInfoEntity2 : productInfoEntity) {
            productVOlList.add(this.convertEntityToVO(productInfoEntity2));
        }
        return productVOlList;
    }

    public Page<ProductMainVO> searchMultipleCondition(ProductSearchCond cond, Pageable pageable) {
        return piRepo.findVOByMultiCondition(cond, pageable);
    }


    private ProductVO convertEntityToVO(ProductInfoEntity pInfoEntity) {
        return ProductVO.builder()
        .seq(pInfoEntity.getSeq())
        .name(pInfoEntity.getName())
        .type(pInfoEntity.getType().getTitle())
        .level(pInfoEntity.getLevel())
        .sweetness(pInfoEntity.getSweetness())
        .sour(pInfoEntity.getSour())
        .soda(pInfoEntity.getSoda())
        .raw(pInfoEntity.getRaw().getTitle())
        .img(pInfoEntity.getImg())
        .subName(pInfoEntity.getSubName())
        .detailImg(pInfoEntity.getDetailImg())
        .detailContent(pInfoEntity.getDetailContent())
        .manufacturer(pInfoEntity.getManufacturer())
        .build();
    }

}
