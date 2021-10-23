package com.jujeol.drink.drink.domain.repository;

import static com.jujeol.drink.drink.domain.QDrink.drink;

import com.jujeol.drink.drink.domain.Drink;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
    public List<Drink> findByIds(List<Long> iDs) {
        return queryFactory.selectFrom(drink)
                .innerJoin(drink.category)
                .where(drink.id.in(iDs))
                .fetchJoin()
                .fetch();
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
