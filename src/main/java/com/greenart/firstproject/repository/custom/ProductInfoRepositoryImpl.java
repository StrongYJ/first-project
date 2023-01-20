package com.greenart.firstproject.repository.custom;

import static com.greenart.firstproject.entity.QProductInfoEntity.productInfoEntity;
import static com.greenart.firstproject.entity.QOptionInfoEntity.optionInfoEntity;
import static com.greenart.firstproject.entity.QReviewEntity.reviewEntity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.entity.QOptionInfoEntity;
import com.greenart.firstproject.entity.enums.AlcoholType;
import com.greenart.firstproject.entity.enums.LevelRangeCode;
import com.greenart.firstproject.entity.enums.RawMaterial;
import com.greenart.firstproject.vo.product.ProductMainVO;
import com.greenart.firstproject.vo.product.ProductSearchCond;
import com.greenart.firstproject.vo.product.QProductMainVO;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductInfoRepositoryImpl implements ProductRepositoryCustom {
    
    private final JPAQueryFactory queryFactory;


    @Override
    public Page<ProductMainVO> findVOByMultiCondition(ProductSearchCond condition, Pageable pageable) {
        List<ProductMainVO> content = queryFactory
            .select(
                new QProductMainVO(
                    productInfoEntity.seq, productInfoEntity.name,
                    productInfoEntity.subName, optionInfoEntity.price.min(),
                    productInfoEntity.img,
                    reviewEntity.grade.avg(), reviewEntity.countDistinct()
                    ))
            .from(productInfoEntity)
            .leftJoin(optionInfoEntity).on(productInfoEntity.eq(optionInfoEntity.product))
            .leftJoin(reviewEntity).on(productInfoEntity.eq(reviewEntity.product))
            .fetchJoin()
            .where(
                typeEq(condition.getType()), levelEq(condition.getLevel()), 
                sweetnessEq(condition.getSweetness()), sourEq(condition.getSour()), 
                sodaEq(condition.getSoda()), rawEq(condition.getRaw())
                )
            .groupBy(productInfoEntity)
            .having(priceBetween(optionInfoEntity.price.min(), condition.getPrice()))
            .orderBy(getOrderSpecifier(pageable.getSort()).stream().toArray(OrderSpecifier[]::new))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }


    private BooleanExpression priceBetween(NumberExpression<Integer> targetPrice, List<Integer> conditionPrice) {
        if(conditionPrice == null) return null;
        return targetPrice.between(conditionPrice.get(0), conditionPrice.get(1));
    }

    private BooleanExpression rawEq(String raw) {
        if(!StringUtils.hasText(raw)) return null;
        return productInfoEntity.raw.eq(RawMaterial.valueOfCode(raw));
    }

    private BooleanExpression sodaEq(Integer soda) {
        if(soda == null) return null;
        return productInfoEntity.soda.eq(soda);
    }

    private BooleanExpression sourEq(Integer sour) {
        if(sour == null) return null;
        return productInfoEntity.sour.eq(sour);
    }

    private BooleanExpression sweetnessEq(Integer sweetness) {
        if(sweetness == null) return null;
        return productInfoEntity.sweetness.eq(sweetness);
    }

    private BooleanExpression levelEq(String level) {
        if(!StringUtils.hasText(level)) return null;
        
        NumberPath<Double> eLevel = productInfoEntity.level;
        return switch(LevelRangeCode.valueOfCode(level)) {
            case LOW -> eLevel.goe(0.0).and(eLevel.lt(10.0));
            case MIDDLE -> eLevel.goe(10.0).and(eLevel.lt(20.0));
            case HIGH -> eLevel.goe(20.0).and(eLevel.lt(30.0));
            case VERY_HIGH -> eLevel.goe(30.0);
            default -> null;
        };
    }

    private BooleanExpression typeEq(String type) {
        if(!StringUtils.hasText(type)) return null;
        return productInfoEntity.type.eq(AlcoholType.valueOfCode(type));
    }

    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            switch(prop) {
                case "mainPrice" -> orders.add(new OrderSpecifier(direction, optionInfoEntity.price.min()));
                case "reviewGrade" -> orders.add(new OrderSpecifier(direction, reviewEntity.grade.avg()));
                case "reviewNumber" -> orders.add(new OrderSpecifier(direction, reviewEntity.countDistinct()));
                default -> orders.add(new OrderSpecifier(direction, new PathBuilder(ProductMainVO.class, "productInfoEntity")));
            };
        });
        return orders;
        
    }
}
