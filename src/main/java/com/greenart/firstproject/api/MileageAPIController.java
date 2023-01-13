package com.greenart.firstproject.api;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.config.MySessionkeys;
import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.repository.MilegePointRepository;
import com.greenart.firstproject.service.MileageService;
import com.greenart.firstproject.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mileage")
@RequiredArgsConstructor
public class MileageAPIController {
    private final MilegePointRepository mileageRepo;
    private final UserService userService;
    private final MileageService mileageServie;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getUserMileage(HttpSession session) {
        Map<String, Object> map = new LinkedHashMap<>();
        Object loginUser = session.getAttribute(MySessionkeys.USER_LOGIN_KEY);
        map.put("data", mileageServie.getUserMileage((UserEntity)loginUser));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
