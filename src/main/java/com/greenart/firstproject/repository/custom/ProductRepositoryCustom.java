package com.greenart.firstproject.repository.custom;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.vo.product.ProductMainVO;
import com.greenart.firstproject.vo.product.ProductSearchCond;

public interface ProductRepositoryCustom {
    Page<ProductMainVO> findVOByMultiCondition(ProductSearchCond condition, Pageable pageable);
}
