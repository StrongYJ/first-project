package com.greenart.firstproject.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.greenart.firstproject.entity.MarketInfoEntity;
import com.greenart.firstproject.entity.MarketStockEntity;
import com.greenart.firstproject.entity.OptionInfoEntity;
import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.repository.MarketInfoRepository;
import com.greenart.firstproject.repository.MarketStockRepository;
import com.greenart.firstproject.repository.OptionInfoRepository;
import com.greenart.firstproject.repository.ProductInfoRepository;
import com.greenart.firstproject.vo.localadmin.LocalMarketOptionStockVO;
import com.greenart.firstproject.vo.localadmin.MarketOptionStockVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/local")
@RequiredArgsConstructor
public class LocalAdminController {
    private final OptionInfoRepository optionRepo;
    private final MarketInfoRepository marketRepo;
    private final MarketStockRepository stockRepo;
    private final ProductInfoRepository productRepo;

    // 지역관리자 로그인 부산1 대구2 대전3 서울4    
    // /admin/local/{id}

    // /{id} 는 marketInfo의 seq 번호
    // http://localhost:8080/admin/local/3
    // @GetMapping("/{id}") // http주소
    // public String getLocalAdmin(@PathVariable("id") Long id, Model model) {
        // String marketName = marketRepo.findById(id).get().getAddress().substring(0, 2); // 잘라서 앞에 두글자만 가져옴
        // model.addAttribute("marketName", marketName); // html로 내보내기위한 이름 저장

        // // 옵션 목록을 나오게 하자!
        // // List<OptionInfoEntity> options = optionRepo.findAll();
        // // model.addAttribute("optionList", options);
        // List<MarketOptionStockVO> mos = new ArrayList<>();
        // if() { //id 번호와 seq번호가 같다면 해당하는 리스트만 출력하시오. findbyId(id).
        //     for(MarketStockEntity m : stockRepo.findAll()) {
        //         mos.add(MarketOptionStockVO.fromEntity(m));
        //     }
        //     model.addAttribute("mosList", mos);
        // }

    //     Optional<MarketInfoEntity> foundMarket = marketRepo.findById(id);
    //     if(!foundMarket.isPresent()) {
    //         return "redirect:/admin/login";
    //     }
    //     MarketInfoEntity market = foundMarket.get();



    //     return "localadmin/localadmin"; // templates의 주소
    // }

    @GetMapping("/list") // 전체리스트 조회
    public String getLocalList(MarketOptionStockVO data, Model model) {
        // unique랑 primary key  하나만 나올수 밖에 없는거는 entity / 그거 빼고 다 리스티임 SQL문 생각하세요!
        // List<MarketInfoEntity> marketEntity = marketRepo.findByAddress(data.getAddress());

        // List<MarketOptionStockVO> mos = stockRepo.findAll().stream().map(MarketOptionStockVO::fromEntity).toList();

        List<MarketOptionStockVO> mos = new ArrayList<>();
        for(MarketStockEntity m : stockRepo.findAll()) {
            mos.add(MarketOptionStockVO.fromEntity(m));
        }
        model.addAttribute("mosList", mos);

        return "localadmin/list";
    }

    // http://localhost:8080/admin/local/1
    @GetMapping("/{seq}")
    public String getLocalList(@PathVariable("seq") Long seq,LocalMarketOptionStockVO data, Model model) {
            String marketName = marketRepo.findById(seq).get().getAddress().substring(0, 2); // 잘라서 앞에 두글자만 가져옴
            model.addAttribute("marketName", marketName); // html로 내보내기위한 이름 저장

            List<LocalMarketOptionStockVO> lmos = new ArrayList<>();
            for(MarketStockEntity m : stockRepo.findAll()) {
                LocalMarketOptionStockVO lmosVO = LocalMarketOptionStockVO.fromLocalEntity(m);
                if(lmosVO.getSeq() == seq) {
                    lmos.add(lmosVO);
                }
            }
            model.addAttribute("list", lmos);
            return "/localadmin/localadmin";
    }


}
