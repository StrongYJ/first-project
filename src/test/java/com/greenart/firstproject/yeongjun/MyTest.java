package com.greenart.firstproject.yeongjun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.greenart.firstproject.entity.CartInfoEntity;
import com.greenart.firstproject.entity.MarketStockEntity;
import com.greenart.firstproject.entity.OptionInfoEntity;
import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.entity.ReviewEntity;
import com.greenart.firstproject.entity.enums.AlcoholType;
import com.greenart.firstproject.entity.enums.LevelRangeCode;
import com.greenart.firstproject.entity.enums.RawMaterial;
import com.greenart.firstproject.repository.CartInfoRepository;
import com.greenart.firstproject.repository.MarketInfoRepository;
import com.greenart.firstproject.repository.MarketStockRepository;
import com.greenart.firstproject.repository.OptionInfoRepository;
import com.greenart.firstproject.repository.ProductInfoRepository;
import com.greenart.firstproject.repository.ReviewRepository;
import com.greenart.firstproject.repository.UserRepository;
import com.greenart.firstproject.vo.product.ProductMainVO;
import com.greenart.firstproject.vo.product.ProductSearchCond;
import com.greenart.firstproject.vo.review.ReviewVO;

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
    @Autowired private CartInfoRepository cartRepo;
    @Autowired private ReviewRepository reviewRepo;

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

    @Test
    void testCart() {
        assertThat(cartRepo.findByUserAndOptionSeq(utestRepo.findById(1L).get(), 2L)).isNotNull();
    }

    @Test
    void reviewTest() {
        List<ReviewVO> list = reviewRepo.findVOByProductSeq(1L);
        assertThat(list.size()).isEqualTo(4);
        for(var a : list) {
            log.info("data={}",a);
        }
    }
    
    @Test
    void enumMapTest() {
        assertThat(AlcoholType.valueOfCode("takju")).isEqualTo(AlcoholType.TAKJU);
        assertThat(AlcoholType.valueOfCode("wine")).isEqualTo(AlcoholType.WINE);
        assertThat(AlcoholType.valueOfCode("chungju")).isEqualTo(AlcoholType.CHUNGJU);
        assertThat(AlcoholType.valueOfCode("soju")).isEqualTo(AlcoholType.SOJU);
        assertThat(RawMaterial.ETC).isEqualTo(RawMaterial.valueOfCode("etc"));
        assertThat(RawMaterial.FLOWER).isEqualTo(RawMaterial.valueOfCode("flower"));
        assertThat(RawMaterial.FRUIT_VEG).isEqualTo(RawMaterial.valueOfCode("fruit"));
        assertThat(RawMaterial.HERB).isEqualTo(RawMaterial.valueOfCode("herb"));
        assertThat(RawMaterial.GRAIN_NUT).isEqualTo(RawMaterial.valueOfCode("grain-nut"));
    }

    @Test
    void findByType() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<ProductInfoEntity> findByType = productRepo.findByType(AlcoholType.valueOfCode("takju"), pageable);
        assertThat(findByType.getContent().size()).isEqualTo(3);
    }

    @Test
    void searchMultiple() {
        Pageable page = PageRequest.of(0, 20);
        List<Integer> price = new ArrayList<>();
        price.add(10000);
        price.add(20000);
        ProductSearchCond cond = ProductSearchCond.builder().category("takju").price(price).build();
        Page<ProductMainVO> searchMultiple = productRepo.findVOByMultiCondition(cond, page);
        assertThat(searchMultiple.getTotalElements()).isEqualTo(2);
    }
    @Test
    void searchMultiple2() {
        Pageable page = PageRequest.of(0, 100);
        ProductSearchCond cond = ProductSearchCond.builder().build();
        List<ProductMainVO> searchMultiple = productRepo.findVOByMultiCondition(cond, page).getContent();
        String query = "select count(r) from ReviewEntity r where r.product.seq = :seq";
        Long singleResult = em.createQuery(query, Long.class).setParameter("seq", 1).getSingleResult();
        assertThat(singleResult).isEqualTo(4L);
        for(var result : searchMultiple) {
            assertThat(result.getReviewNumber()).isEqualTo(em.createQuery(query, Long.class).setParameter("seq", result.getProductSeq()).getSingleResult());
        }
    }

    // @Test
    // @Rollback(false)
    // void setReview() {
    //     reviewRepo.findAll().forEach(r -> {
    //         r.setOptionName(r.getOption().getOption());
    //         r.setProduct(r.getOption().getProduct());
    //     });
    // }

    // @Test
    // @Rollback(false)
    // void initStock() {
    //     for(OptionInfoEntity o : optionRepo.findAll()) {
    //         marketRepo.findAll().forEach(m -> {
    //             stockRepo.save(new MarketStockEntity(10, m, o));
    //         });
    //     }
    // }
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

