package com.greenart.firstproject.christmasboy;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.entity.QProductInfoEntity;
import com.greenart.firstproject.entity.enums.AlcoholType;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
public class MyTest {
    
    @Autowired 
    private JPAQueryFactory queryFactory;
    @PersistenceContext
    private EntityManager em;

    @Test
    void querydsl() {
        List<ProductInfoEntity> resultList = em.createQuery("select p from ProductInfoEntity p", ProductInfoEntity.class).getResultList();
    }
}
