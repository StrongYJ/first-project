package com.greenart.firstproject.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import com.greenart.firstproject.entity.OptionInfoEntity;
import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.vo.product.OptionVO;
import com.greenart.firstproject.vo.localadmin.LocalMarketOptionStockVO;

@Repository
public interface OptionInfoRepository extends JpaRepository<OptionInfoEntity, Long>{
    List<OptionInfoEntity> findByOptionAndPrice(String option, Integer price);
    List<OptionInfoEntity> findByProduct(ProductInfoEntity product);
    
    @Query(
        value = "select new com.greenart.firstproject.vo.localadmin.LocalMarketOptionStockVO(ms.seq, oi.product.name, oi.option, oi.price, ms.stock) from OptionInfoEntity oi left join MarketStockEntity ms on ms.option.seq = oi.seq and ms.market.seq = :marketInfoSeq"
        )
    List<LocalMarketOptionStockVO> getOptionList(@Param("marketInfoSeq") Long marketInfoSeq);

    @Query(
        value = "select new com.greenart.firstproject.vo.localadmin.LocalMarketOptionStockVO(ms.seq, oi.product.name, oi.option, oi.price, ms.stock) from OptionInfoEntity oi left join MarketStockEntity ms on ms.option.seq = oi.seq and ms.market.seq = :marketInfoSeq"
        )
	Page<LocalMarketOptionStockVO> getOptionList(@Param("marketInfoSeq") Long seq, Pageable pageable);
}
