package com.greenart.firstproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.greenart.firstproject.entity.OptionInfoEntity;
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
    
    // 전체제품조회용
    public Page<ProductInfoEntity> findAllProducts(Pageable pageable) {
        return piRepo.findAll(pageable);
    }

    // 카테고리별조회용
    public Page<ProductInfoEntity> findByType(AlcoholType type, Pageable pageable) {
        return piRepo.findByType(type, pageable);
    }

    // 제품상세페이지
    public ProductVO findProductDetail(Long seq) {
        // ProductInfoEntity product =  piRepo.findById(seq).orElseThrow();
        // List<OptionInfoEntity> options = oiRepo.findByProduct(product);

        ProductInfoEntity product = piRepo.findFetchBySeq(seq).orElseThrow();

        return new ProductVO(product);
    }

    // 제품상세페이지에 옵션출력
    // public List<ProductVO> findProductAndOption(Long seq) {
    //     return oiRepo.findByPiSeq(ProductVO.builder().piSeq(seq).build());
    // }

    // 제품이름으로 제품검색
    public List<ProductVO> searchProducts(String keyword) {
        List<ProductInfoEntity> productInfoEntity = piRepo.findByNameContaining(keyword);
        List<ProductVO> productVOlList = new ArrayList<>();
        
        if(productInfoEntity.isEmpty()) return productVOlList;

        for(ProductInfoEntity productInfoEntity2 : productInfoEntity) {
            productVOlList.add(this.convertEntityToVO(productInfoEntity2));
        }
        return productVOlList;
    }

    // 조건 다중선택
    public Page<ProductMainVO> searchMultipleCondition(ProductSearchCond cond, Pageable pageable) {
        return piRepo.findVOByMultiCondition(cond, pageable);
    }


    private ProductVO convertEntityToVO(ProductInfoEntity pInfoEntity) {
        return ProductVO.builder()
        .productSeq(pInfoEntity.getSeq())
        .productName(pInfoEntity.getName())
        .type(pInfoEntity.getType().getTitle())
        .level(pInfoEntity.getLevel())
        .sweetness(pInfoEntity.getSweetness())
        .sour(pInfoEntity.getSour())
        .soda(pInfoEntity.getSoda())
        .raw(pInfoEntity.getRaw().getTitle())
        .thumbImg( "/api/images/product/" + pInfoEntity.getImg())
        .subName(pInfoEntity.getSubName())
        .detailImg( "/api/images/product/" + pInfoEntity.getDetailImg())
        .detailContent(pInfoEntity.getDetailContent())
        .manufacturer(pInfoEntity.getManufacturer())
        .build();
    }

}
