package com.greenart.firstproject.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.greenart.firstproject.entity.MarketInfoEntity;
import com.greenart.firstproject.entity.OptionInfoEntity;
import com.greenart.firstproject.repository.MarketInfoRepository;
import com.greenart.firstproject.repository.OptionInfoRepository;

@Controller
@RequestMapping("/admin/local")
public class LocalAdminController {
    @Autowired OptionInfoRepository optionRepo;
    @Autowired MarketInfoRepository marketRepo;

    // 지역관리자 로그인 부산1 대구2 대전3 서울4    
    // /admin/local/{id}

    // /{id} 는 marketInfo의 seq 번호
    // http://localhost:8080/admin/local/3
    @GetMapping("/{id}") // http주소
    public String getLocalAdmin(@PathVariable("id") Long id, Model model) {
        String marketName = marketRepo.findById(id).get().getAddress().substring(0, 2); // 잘라서 앞에 두글자만 가져옴
        model.addAttribute("marketName", marketName); // html로 내보내기위한 이름 저장

        // 일단 목록을 나오게 하자!
        List<OptionInfoEntity> options = optionRepo.findAll();

        model.addAttribute("optionList", options);
        

        return "localadmin/localadmin"; // templates의 주소
    }



}
