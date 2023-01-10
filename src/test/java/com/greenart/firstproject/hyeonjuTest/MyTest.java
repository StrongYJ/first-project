package com.greenart.firstproject.hyeonjuTest;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.greenart.firstproject.repository.OptionInfoRepository;
import com.greenart.firstproject.vo.localadmin.LocalMarketOptionStockVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
class MyTest {

	@Autowired private OptionInfoRepository optionRepo;

	@Test
	void contextLoads() {
		List<LocalMarketOptionStockVO> optionList = optionRepo.getOptionList(1L);
		Assertions.assertThat(optionList.size()).isEqualTo(optionRepo.findAll().size());
		for(var o : optionList) {
			log.info("data={}\n", o);
		}
	}

}
