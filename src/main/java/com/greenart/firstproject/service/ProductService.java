package com.greenart.firstproject.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
