package com.greenart.firstproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.greenart.firstproject.entity.MarketInfoEntity;
import com.greenart.firstproject.entity.MarketStockEntity;
import com.greenart.firstproject.entity.OptionInfoEntity;
import com.greenart.firstproject.vo.localadmin.MarketOptionStockVO;

@Repository
public interface MarketStockRepository extends JpaRepository<MarketStockEntity,Long>{
<<<<<<< HEAD
    List<MarketStockEntity> findByMarket(MarketInfoEntity market);
    List<MarketStockEntity> findByOption(OptionInfoEntity option);
=======
    MarketStockEntity findBySeq(Long seq);
    Page<MarketStockEntity> findAll(Pageable pageable);
    MarketStockEntity findBySeqAndStock(Long seq, Integer stock);
>>>>>>> hyeonju
}
                        