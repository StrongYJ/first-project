package com.greenart.firstproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.repository.OptionInfoRepository;
import com.greenart.firstproject.repository.ProductInfoRepository;
import com.greenart.firstproject.vo.OptionVO;
import com.greenart.firstproject.vo.ProductVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductInfoRepository piRepo;
    private final OptionInfoRepository oiRepo;

    // 전체제품 조회용
    public Object findAllProducts(Pageable pageable) {
        return piRepo.findAll(pageable);
    }

    // 카테고리별 제품 조회용
    public Object findByType(String type, Pageable pageable) {
        return piRepo.findByType(type, pageable);
    }

    // seq번호로 제품정보 조회용
    public Object findProductDetail(ProductVO productVO) {
        return piRepo.findBySeq(productVO.getSeq());
    }

    // 제품이름으로 검색용
    public List<ProductVO> searchProducts(String keyword) {
        List<ProductInfoEntity> productInfoEntity = piRepo.findByNameContaining(keyword);
        List<ProductVO> productVOlList = new ArrayList<>();
        
        if(productInfoEntity.isEmpty()) return productVOlList;

        for(ProductInfoEntity productInfoEntity2 : productInfoEntity) {
            productVOlList.add(this.convertEntityToVO(productInfoEntity2));
        }
        return productVOlList;
    }



    private ProductVO convertEntityToVO(ProductInfoEntity pInfoEntity) {
        return ProductVO.builder()
        .seq(pInfoEntity.getSeq())
        .name(pInfoEntity.getName())
        .type(pInfoEntity.getType())
        .level(pInfoEntity.getLevel())
        .sweetness(pInfoEntity.getSweetness())
        .sour(pInfoEntity.getSour())
        .soda(pInfoEntity.getSoda())
        .raw(pInfoEntity.getRaw())
        .img(pInfoEntity.getImg())
        .subName(pInfoEntity.getSubName())
        .detailImg(pInfoEntity.getDetailImg())
        .detailContent(pInfoEntity.getDetailContent())
        .manufacturer(pInfoEntity.getManufacturer())
        .build();
    }
}
