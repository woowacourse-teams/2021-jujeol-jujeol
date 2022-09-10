package com.jujeol.drink.rds.repository;

import com.jujeol.drink.domain.model.DrinkSort;
import com.jujeol.drink.rds.entity.DrinkEntity;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static com.jujeol.drink.rds.entity.QDrinkEntity.drinkEntity;

@RequiredArgsConstructor
public class DrinkRepositoryCustomImpl implements DrinkRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<DrinkEntity> findByKeywords(List<String> keywords, Pageable pageable) {
        List<DrinkEntity> contents = queryFactory.selectFrom(drinkEntity)
                                              .join(drinkEntity.category).fetchJoin()
                                              .where(whereKeywords(keywords))
                                              .offset(pageable.getOffset())
                                              .limit(pageable.getPageSize())
                                              .fetch();
        return toPage(pageable, contents);
    }

    private Predicate[] whereKeywords(List<String> keywords) {
        List<Predicate> predicates = new ArrayList<>();

        for (String keyword : keywords) {
            predicates.add(drinkEntity.name.like("%" + keyword + "%")
                                           .or(drinkEntity.englishName.like("%" + keyword + "%")));
        }

        return predicates.toArray(new Predicate[0]);
    }

    @Override
    public Page<DrinkEntity> findAllByCategoryName(String category, Pageable pageable) {
        List<DrinkEntity> contents = queryFactory.selectFrom(drinkEntity)
                                              .join(drinkEntity.category).fetchJoin()
                                              .where(drinkEntity.category.name.eq(category))
                                              .offset(pageable.getOffset())
                                              .limit(pageable.getPageSize())
                                              .fetch();
        return toPage(pageable, contents);
    }

    @Override
    public Page<DrinkEntity> findAllWithSort(Pageable pageable, DrinkSort drinkSort) {
        JPAQuery<DrinkEntity> query = queryFactory.selectFrom(drinkEntity)
                                                  .join(drinkEntity.category).fetchJoin()
                                                  .offset(pageable.getOffset())
                                                  .limit(pageable.getPageSize());
        switch (drinkSort) {
            case PREFERENCE_AVG:
                return toPage(pageable, query.orderBy(drinkEntity.preferenceAvg.asc()).fetch());
            case NO_SORT:
            default:
                return toPage(pageable, query.fetch());
        }
    }

    @Override
    public Page<DrinkEntity> findAllByCategoryNameWithSort(String category, Pageable pageable, DrinkSort drinkSort) {
        JPAQuery<DrinkEntity> query = queryFactory.selectFrom(drinkEntity)
                                                  .join(drinkEntity.category).fetchJoin()
                                                  .where(drinkEntity.category.name.eq(category))
                                                  .offset(pageable.getOffset())
                                                  .limit(pageable.getPageSize());
        switch (drinkSort) {
            case PREFERENCE_AVG:
                return toPage(pageable, query.orderBy(drinkEntity.preferenceAvg.asc()).fetch());
            case NO_SORT:
            default:
                return toPage(pageable, query.fetch());
        }
    }

    private PageImpl<DrinkEntity> toPage(Pageable pageable, List<DrinkEntity> contents) {
        return new PageImpl<>(contents, pageable, contents.size());
    }
}
