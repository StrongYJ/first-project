package com.greenart.firstproject.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.config.MySessionkeys;
import com.greenart.firstproject.repository.MilegePointRepository;
import com.greenart.firstproject.service.MileageService;
import com.greenart.firstproject.service.UserService;
import com.greenart.firstproject.vo.MileageInfoVO;

import com.greenart.firstproject.entity.UserEntity;
import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mileage")
@RequiredArgsConstructor
public class MileageAPIController {
    private final MilegePointRepository mileageRepo;
    private final UserService userService;
    private final MileageService mileageService;

    // @GetMapping("")
    // public ResponseEntity<Map<String, Object>> getUserMileage(HttpSession session) {
    //     Map<String, Object> map = new LinkedHashMap<>();
    //     Object loginUser = session.getAttribute(MySessionkeys.USER_LOGIN_KEY);
    //     map.put("data", mileageService.getUserMileage((UserEntity)loginUser));
    //     return new ResponseEntity<>(map, HttpStatus.OK);
    // }
    
    // seq 번호로 받아서 출력
    @GetMapping("/{seq}")
    public ResponseEntity<Map<String, Object>> getUserMileage(@PathVariable("seq")Long uiSeq){
        Map<String, Object> map = new LinkedHashMap<>();
        List<MileageInfoVO> mileage =  mileageService.getUserMileage(uiSeq);
        map.put("data", mileage);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
