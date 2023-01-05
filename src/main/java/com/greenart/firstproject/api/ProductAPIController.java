package com.greenart.firstproject.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.repository.ProductInfoRepository;
import com.greenart.firstproject.service.ProductService;
import com.greenart.firstproject.vo.ProductVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
// @RequiredArgsConstructor
public class ProductAPIController {
    @Autowired ProductInfoRepository piRepo;
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllProducts(Pageable pageable) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("list", piRepo.findAll(pageable));
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @GetMapping("/category")
    public ResponseEntity<Map<String, Object>> getCategoryProducts(@RequestParam String type, Pageable pageable) {
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        map.put("list", piRepo.findByType(type, pageable));
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }
}
