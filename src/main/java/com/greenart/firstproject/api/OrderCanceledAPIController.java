package com.greenart.firstproject.api;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.service.OrderCanceledService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderCanceledAPIController {
    private final OrderCanceledService ordercanceledService;

    @GetMapping() {
        public ResponseEntity<Map<String,Object>> getOrderCanceled(@PathVariable("seq") Long uiSeq) {
            Map<String, Object> map = new LinkedHashMap<>();

        }
    }

}
