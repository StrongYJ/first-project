package com.greenart.firstproject.service;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.repository.MarketInfoRepository;
import com.greenart.firstproject.repository.OptionInfoRepository;
import com.greenart.firstproject.repository.ProductInfoRepository;
import com.greenart.firstproject.vo.ProductVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductInfoRepository piRepo;
    private final OptionInfoRepository oiRepo;
    private final MarketInfoRepository miRepo;

    public Page<ProductVO> getAllProducts(Pageable pageable) {

    }
}
