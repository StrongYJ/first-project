package com.greenart.firstproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.repository.ProductInfoRepository;
import com.greenart.firstproject.vo.ProductVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductInfoRepository piRepo;

    public Object findAllProducts(Pageable pageable) {
        return piRepo.findAll();
    }
    public List findByType(String type, Pageable pageable) {
        return piRepo.findByType(type, pageable);
    }
    public Object findProductDetail(ProductVO productVO) {
        return piRepo.findBySeq(productVO.getSeq());
    }
    // public List<ProductVO> searchProduts(String keyword) {
    //     return piRepo.findAllSearch(keyword).stream()
    //         .map(ProductVO::new).collect(Collectors.toList());
    // }
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
        .manufacturer(pInfoEntity.getManu())
        .build();
    }
}
