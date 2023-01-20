package com.greenart.firstproject.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.entity.enums.AlcoholType;
import com.greenart.firstproject.service.ProductService;
import com.greenart.firstproject.vo.product.ProductSearchCond;
import com.greenart.firstproject.vo.product.ProductVO;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductAPIController {
    private final ProductService piService;


    @GetMapping("/categories")
    public ResponseEntity<Map<String, Object>> getAlcoholTypes() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("size", AlcoholType.values().length);
        map.put("data", AlcoholType.values());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // 전체제품정보 싹다조회
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllProductsInfo(Pageable pageable) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("list", piService.findAllProducts(pageable));
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }
    
    // 카테고리별 제품조회
    @GetMapping("/category/{code}")
    public ResponseEntity<Map<String, Object>> getCategoryProducts(@PathVariable("code") String id, Pageable pageable) {
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        map.put("list", piService.findByType(AlcoholType.valueOfCode(id), pageable));
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }

    // 상품번호로 상품조회(상세페이지) + 옵션추가
    @GetMapping("/detail/{seq}")
    public ResponseEntity<Map<String, Object>> getProductDetail(@PathVariable("seq") Long seq) {
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        map.put("detailInfo", piService.findProductDetail(ProductVO.builder().seq(seq).build()));
        // map.put("optionInfo", piService.findProductOption(OptionVO.builder().seq(seq).build()));
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }

    // 검색기능
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchProducts(@RequestParam(value="keyword") String keyword) {
        List<ProductVO> productVOs = piService.searchProducts(keyword);
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        map.put("productList", productVOs);
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }

    // 상품번호로 조회 + 옵션까지 같이
    // @GetMapping("/detail")


    // 여러 조건으로 검색
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> searchMultipleCondition(ProductSearchCond cond, Pageable pageable) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("data", piService.searchMultipleCondition(cond, pageable));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
