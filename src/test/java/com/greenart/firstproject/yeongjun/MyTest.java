package com.greenart.firstproject.yeongjun;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.greenart.firstproject.entity.MarketStockEntity;
import com.greenart.firstproject.entity.OptionInfoEntity;
import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.repository.MarketInfoRepository;
import com.greenart.firstproject.repository.MarketStockRepository;
import com.greenart.firstproject.repository.OptionInfoRepository;
import com.greenart.firstproject.repository.ProductInfoRepository;
import com.greenart.firstproject.repository.UserRepository;
import com.greenart.firstproject.vo.UserJoinVO;
import com.greenart.firstproject.vo.superadmin.AdminOptionVO;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
class MyTest {
    
    @Autowired private UserRepository utestRepo;
    @Autowired private OptionInfoRepository optionRepo;
    @Autowired private MarketStockRepository stockRepo;
    @Autowired private MarketInfoRepository marketRepo;
    @Autowired private ProductInfoRepository productRepo;

    @Autowired
    private EntityManager em;

    @Test
    void noTwoWayMappingJoinUsingJPQL() {
        // select * from option_info oi left join market_stock ms on ms.ms_oi_seq = oi.oi_seq and ms.ms_mi_seq = 1;
        String query = "select oi.option, ms.stock from OptionInfoEntity oi left join MarketStockEntity ms on ms.option.seq = oi.seq and ms.market.seq = 1";
        List<TestClass> list = em.createQuery(query).getResultList().stream().map(o -> new TestClass((Object[])o)).toList();
        log.info("data={}", list);
    }

    @Test
    void nativeQueryTest() {
        String query = "select oi.oi_option, ms.ms_stock from option_info oi left join market_stock ms on ms.ms_oi_seq = oi.oi_seq and ms.ms_mi_seq = 1";
        List<TestClass> list = em.createNativeQuery(query).getResultList().stream().map(o -> new TestClass((Object[])o)).toList();
        log.info("data={}", list);
    }

    // @Test
    // void testSetParam() {
    //     String query = "select oi.option, s.stock from OptionInfoEntity oi left join oi.stocks s on s = :stock ";
    //     List<MarketStockEntity> m = stockRepo.findByMarket(marketRepo.findById(1L).get());
    //     var resultList = em.createQuery(query).setParameter("stock", m).getResultList().stream().map(o -> new TestClass(o)).toList();
    //     log.info("data={}", resultList);
    // }

    @Test
    void test2() {
        String query = "select oi from OptionInfoEntity oi left join oi.stocks s";
        var resultList = em.createQuery(query).getResultList();
        log.info("data={} \n", resultList);
    }

    @Test
    void noTwoWayMappingJoinUsingJPQL2() {
        // select * from option_info oi left join market_stock ms on ms.ms_oi_seq = oi.oi_seq and ms.ms_mi_seq = 1;
        String query = "select new com.greenart.firstproject.yeongjun.LocalMainStockDTO(oi.option, ms.stock) from OptionInfoEntity oi left join MarketStockEntity ms on ms.option.seq = oi.seq and ms.market.seq = 1";
        List<LocalMainStockDTO> list = em.createQuery(query, LocalMainStockDTO.class).getResultList();
        for(var dto : list) {
            log.info("data={} \n", dto);
        }
    }


    private class TestClass {
        String title;
        Integer stock;

        public TestClass(Object o) {
            Object[] o2 = (Object[])o;
            this.title = (String)o2[0];
            this.stock = (Integer)o2[1];
        }

        @Override
        public String toString() {
            return "TestClass [title=" + title + ", stock=" + stock + "]" +"\n";
        }
    }
    
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class LocalMainStockDTO {
    private String optionName;
    private Integer stock;

    // LocalMainStockDTO(String option, Integer stock) {
    //     this.optionName = option;
    //     this.stock = stock;
    // }
}

