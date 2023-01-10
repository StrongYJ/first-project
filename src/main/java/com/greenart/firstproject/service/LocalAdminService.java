package com.greenart.firstproject.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenart.firstproject.entity.MarketInfoEntity;
import com.greenart.firstproject.entity.MarketStockEntity;
import com.greenart.firstproject.entity.OptionInfoEntity;
import com.greenart.firstproject.repository.MarketStockRepository;
import com.greenart.firstproject.repository.OptionInfoRepository;
import com.greenart.firstproject.vo.localadmin.LocalMarketOptionStockVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // 필요한 필드의 생성자의 초기화 필드를 자동으로 해줌
public class LocalAdminService {
    // final는 초기화필요
    private final OptionInfoRepository optionRepo;
    private final MarketStockRepository stockRepo;

    public List<LocalMarketOptionStockVO> getOptionList(Long seq) {
        List<LocalMarketOptionStockVO> optionList = optionRepo.getOptionList(seq);
        return optionList;
    }
    public Page<LocalMarketOptionStockVO> getOptionList(Long seq, Pageable pageable) {
        Page<LocalMarketOptionStockVO> optionList = optionRepo.getOptionList(seq, pageable);
        return optionList;
    }

}
