package com.jujeol.drink.drink.domain.repository;

import static com.jujeol.drink.drink.domain.QDrink.drink;

import com.jujeol.drink.drink.domain.Drink;
import com.jujeol.drink.drink.domain.SearchWords;
import com.jujeol.drink.recommend.domain.RecommendationTheme;
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
    public List<Drink> findBySearch(SearchWords searchWords, List<String> categoryNames) {
        return queryFactory.selectFrom(drink)
                .innerJoin(drink.category)
                .where(searchCondition(searchWords, categoryNames))
                .fetchJoin()
                .fetch();
    }

    private BooleanBuilder searchCondition(SearchWords searchWords, List<String> categoryNames) {
        BooleanBuilder builder = new BooleanBuilder();
        if (searchWords.hasSearchWords()) {
            for (String word : searchWords.getSearchWords()) {
                if (categoryNames.contains(word)) {
                    builder.or(drink.name.name.eq("절대절대절대검색되지않고세상에존재하지않는이름"));
                    continue;
                }
                builder.or(drink.name.name.likeIgnoreCase("%" + word + "%"));
                builder.or(drink.englishName.englishName.likeIgnoreCase("%" + word + "%"));
            }
        }
        return builder;
    }

    @Override
    public List<Drink> findByCategory(SearchWords searchWords, String categoryKey) {
        return queryFactory.selectFrom(drink)
                .innerJoin(drink.category)
                .where(categorySearchCondition(searchWords, categoryKey))
                .fetchJoin()
                .fetch();
    }

    private BooleanBuilder categorySearchCondition(SearchWords searchWords, String categoryKey) {
        BooleanBuilder builder = new BooleanBuilder();
        if (searchWords.hasSearchWords()) {
            for (String word : searchWords.getSearchWords()) {
                builder.or(drink.category.name.eq(word));
            }
        }
        if (categoryKey != null) {
            builder.or(drink.category.key.eq(categoryKey));
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
        if (recommendationTheme.isBest()) {
            builder.and(drink.preferenceAvg.gt(0));
        }
        return builder;
    }

    private OrderSpecifier<?> recommendationOrder(RecommendationTheme recommendationTheme) {
        if (recommendationTheme.isPreference()) {
            return drink.preferenceAvg.desc();
        }
        if (recommendationTheme.isBest()) {
            return drink.preferenceAvg.multiply(0.7)
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
        private final String smallImageFilePath;
        private final String mediumImageFilePath;
        private final String largeImageFilePath;
        private final Long categoryId;
        private final Double preferenceAvg;
        private final String description;

        public static DrinkDto create(Drink drink) {
            return new DrinkDto(
                    drink.getId(),
                    drink.getName(),
                    drink.getEnglishName(),
                    drink.getAlcoholByVolume(),
                    drink.getSmallImageFilePath(),
                    drink.getMediumImageFilePath(),
                    drink.getLargeImageFilePath(),
                    drink.getCategory().getId(),
                    drink.getPreferenceAvg(),
                    drink.getDescription()
            );
        }
    }
}
