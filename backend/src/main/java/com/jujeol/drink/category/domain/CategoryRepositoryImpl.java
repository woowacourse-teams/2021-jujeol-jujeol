package com.jujeol.drink.category.domain;

import static com.jujeol.drink.category.domain.QCategory.category;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findAllName() {
        return queryFactory.select(category.name)
                .from(category)
                .fetch();
    }
}
