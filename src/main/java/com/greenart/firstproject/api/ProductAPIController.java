package com.greenart.firstproject.api;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.service.ProductService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductAPIController {
    private final ProductService piService;

    // 전체제품정보 싹다조회
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllProductsInfo(Pageable pageable) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("list", piService.findAllProducts(pageable));
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }
    
    // 카테고리별 제품조회
    @GetMapping("/category/{id}")
    public ResponseEntity<Map<String, Object>> getCategoryProducts(@PathVariable String id, Pageable pageable) {
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        map.put("list", piService.findByType(id, pageable));
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }

    // 카테고리 조회
    
}