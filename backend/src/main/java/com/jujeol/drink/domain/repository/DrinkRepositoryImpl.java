package com.jujeol.drink.domain.repository;

import static com.jujeol.drink.domain.QDrink.drink;

import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.RecommendationTheme;
import com.jujeol.drink.domain.Search;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class DrinkRepositoryImpl implements DrinkCustomRepository {

    private final SimpleJdbcInsert jdbcInsert;
    private final JPAQueryFactory queryFactory;

    public DrinkRepositoryImpl(DataSource dataSource, JPAQueryFactory queryFactory) {
        this.jdbcInsert =
                new SimpleJdbcInsert(dataSource)
                        .withTableName("drink")
                        .usingGeneratedKeyColumns("id");
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Drink> findBySearch(Search search, Pageable pageable) {
        QueryResults<Drink> result = queryFactory.selectFrom(drink)
                .where(searchCondition(search))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    private BooleanBuilder searchCondition(Search search) {
        BooleanBuilder builder = new BooleanBuilder();
        if (search.hasSearch()) {
            builder.and(
                    drink.name.name.like("%" + search.getSearch() + "%")
                            .or(drink.englishName.englishName.like("%" + search.getSearch() + "%"))
            );
        }
        if (search.hasCategoryKey()) {
            builder.and(drink.category.key.equalsIgnoreCase(search.getCategoryKey()));
        }
        return builder;
    }

    @Override
    public Page<Drink> findByRecommendation(
            RecommendationTheme recommendationTheme,
            Pageable pageable
    ) {
        QueryResults<Drink> result = queryFactory.selectFrom(drink)
                .where(recommendationCondition(recommendationTheme))
                .orderBy(recommendationOrder(recommendationTheme))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    private BooleanBuilder recommendationCondition(RecommendationTheme recommendationTheme) {
        BooleanBuilder builder = new BooleanBuilder();
        if (recommendationTheme.isPreference()) {
            builder.and(drink.preferenceAvg.gt(0));
        }
        if (recommendationTheme.isViewCount()) {
            builder.and(drink.viewCount.viewCount.gt(0));
        }
        if (recommendationTheme.isBest()) {
            builder.and(drink.preferenceAvg.gt(0));
            builder.and(drink.viewCount.viewCount.gt(0));
        }
        return builder;
    }

    private OrderSpecifier<?> recommendationOrder(RecommendationTheme recommendationTheme) {
        if (recommendationTheme.isPreference()) {
            return drink.preferenceAvg.desc();
        }
        if (recommendationTheme.isViewCount()) {
            return drink.viewCount.viewCount.desc();
        }
        if (recommendationTheme.isBest()) {
            return drink.preferenceAvg.multiply(0.7)
                    .add(drink.viewCount.viewCount.multiply(0.3))
                    .desc();
        }
        return drink.id.asc();
    }

    @Override
    @Transactional
    public void batchInsert(List<Drink> drinks) {
        List<DrinkDto> drinkDtos = drinks.stream()
                .map(DrinkDto::create)
                .collect(Collectors.toList());

        final SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(drinkDtos);
        jdbcInsert.executeBatch(batch);
    }

    @AllArgsConstructor
    @Getter
    static class DrinkDto {

        private final Long id;
        private final String name;
        private final String englishName;
        private final Double alcoholByVolume;
        private final String imageFilePath;
        private final Long categoryId;
        private final Double preferenceAvg;
        private final Long viewCountId;

        public static DrinkDto create(Drink drink) {
            return new DrinkDto(
                    drink.getId(),
                    drink.getName(),
                    drink.getEnglishName(),
                    drink.getAlcoholByVolume(),
                    drink.getImageFilePath(),
                    drink.getCategory().getId(),
                    drink.getPreferenceAvg(),
                    drink.getViewCount().getId()
            );
        }
    }
}
