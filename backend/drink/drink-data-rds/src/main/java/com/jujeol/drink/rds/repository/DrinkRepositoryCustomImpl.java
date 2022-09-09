package com.jujeol.drink.rds.repository;

import com.jujeol.drink.rds.entity.DrinkEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class DrinkRepositoryCustomImpl implements DrinkRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<DrinkEntity> findByKeywords(List<String> keywords, Pageable pageable) {
        // TODO : for 문으로 where 문 조건 합치기
//        queryFactory.selectFrom(drink)
//            .join(drink.category, category)
//            .fetchJoin()
//            .where(drink.name.name.like("%" + keyword + "%")
//                .or(drink.englishName.englishName.like("%" + keyword + "%")))
//            .fetch();
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DrinkEntity> findAllByCategoryName(String category, Pageable pageable) {
        // TODO : findByCategoryName
        return null;
    }

    @Override
    public Page<DrinkEntity> findAllWithSort(Pageable pageable) {
        // TODO : findAllWithSort
        return null;
    }

    @Override
    public Page<DrinkEntity> findAllByCategoryNameWithSort(String category, Pageable pageable) {
        // TODO : findAllByCategoryNameWithSort
        return null;
    }
}
