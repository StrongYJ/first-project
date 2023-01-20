package com.greenart.firstproject.controller.localadmin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.greenart.firstproject.config.MySessionkeys;
import com.greenart.firstproject.entity.MarketInfoEntity;
import com.greenart.firstproject.entity.MarketStockEntity;
import com.greenart.firstproject.entity.OptionInfoEntity;
import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.repository.MarketInfoRepository;
import com.greenart.firstproject.repository.MarketStockRepository;
import com.greenart.firstproject.repository.OptionInfoRepository;
import com.greenart.firstproject.repository.ProductInfoRepository;
import com.greenart.firstproject.service.LocalAdminService;
import com.greenart.firstproject.vo.localadmin.LocalMarketOptionStockVO;
import com.greenart.firstproject.vo.localadmin.MarketOptionStockVO;
import com.greenart.firstproject.vo.localadmin.UpdateLocalMarketOptionStockVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/local/")
@RequiredArgsConstructor
public class LocalAdminController {
    private final MarketInfoRepository marketRepo;
    private final MarketStockRepository stockRepo;
    private final LocalAdminService localService;
    private final OptionInfoRepository optionRepo;
    // 지역관리자 로그인 부산1 대구2 대전3 서울4    
    // /admin/local/{id}

    // /{id} 는 marketInfo의 seq 번호
    // http://192.168.0.183:8080/admin/local/1
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
    public String getLocalList(MarketOptionStockVO data, Model model, HttpSession session) {
        // if(session.getAttribute(MySessionkeys.LOCAL_ADMIN_KEY) == null) {
        //     return "redirect:/admin/login";
        // }
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
    // @GetMapping("/{seq}")
    // public String getLocalList(@PathVariable("seq") Long seq,LocalMarketOptionStockVO data, Model model) {
    //         String marketName = marketRepo.findById(seq).get().getAddress().substring(0, 2); // 잘라서 앞에 두글자만 가져옴
    //         model.addAttribute("marketName", marketName); // html로 내보내기위한 이름 저장

    //         List<LocalMarketOptionStockVO> lmos = new ArrayList<>();
    //         for(MarketStockEntity m : stockRepo.findAll()) {
    //             LocalMarketOptionStockVO lmosVO = LocalMarketOptionStockVO.fromLocalEntity(m);
    //             if(lmosVO.getSeq() == seq) {
    //                 lmos.add(lmosVO);
    //             }
    //         }
    //         model.addAttribute("list", lmos);
    //         return "/localadmin/localadmin";
    // }

    @GetMapping("/{seq}")
    public String getLocalList(@PathVariable("seq") Long seq, Model model,/*@PageableDefault(size=20)*/ Pageable pageable) {
            String marketName = marketRepo.findById(seq).get().getName();
            model.addAttribute("marketName", marketName); // html로 내보내기위한 이름 저장
            model.addAttribute("seq", seq);
            Page<LocalMarketOptionStockVO> lmos = localService.getOptionList(seq, pageable);
            // int nowPage = lmos.getPageable().getPageNumber()+1;
            // int startPage = Math.max(nowPage -4, 1);
            // int endPage = Math.min(nowPage +5, lmos.getTotalPages());

            model.addAttribute("list", lmos.getContent());
            model.addAttribute("pages", lmos);

            // model.addAttribute("nowPage", nowPage);
            // model.addAttribute("startPage", startPage);
            // model.addAttribute("endPage", endPage);

            return "/localadmin/localadmin";
    }

    @GetMapping("/stock")
    public String getLocalStock(@RequestParam Long stock_no, Model model) {
        MarketStockEntity marketStock = localService.getStockInfo(stock_no);
        UpdateLocalMarketOptionStockVO ulmos = new UpdateLocalMarketOptionStockVO();
        ulmos.setMarketName(marketStock.getMarket().getName());
        ulmos.setProductName(marketStock.getOption().getProduct().getName());
        ulmos.setOptionName(marketStock.getOption().getOption());
        ulmos.setOptionPrice(marketStock.getOption().getPrice());
        ulmos.setStock(marketStock.getStock());

        model.addAttribute("marketSeq", marketStock.getMarket().getSeq());
        model.addAttribute("market_stock", ulmos);
        return "/localadmin/stock";
    }

    @PostMapping("/stock")
    public String postLocalStock(@RequestParam Long stock_no, @RequestParam Integer stock, RedirectAttributes reat) {
        localService.updateStock(stock_no, stock);

        reat.addAttribute("stock_no", stock_no);
        reat.addAttribute("updated", true);
        return "redirect:/admin/local/stock";
    }

}
