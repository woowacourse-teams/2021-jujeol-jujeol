package com.jujeol.drink.domain.repository;

import com.jujeol.drink.domain.Drink;
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

    public DrinkRepositoryImpl(DataSource dataSource) {
        this.jdbcInsert =
                new SimpleJdbcInsert(dataSource)
                .withTableName("drink")
                .usingGeneratedKeyColumns("id");
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

        public static DrinkDto create(Drink drink) {
            return new DrinkDto(
                    drink.getId(),
                    drink.getName(),
                    drink.getEnglishName(),
                    drink.getAlcoholByVolume(),
                    drink.getImageFilePath(),
                    drink.getCategory().getId(),
                    drink.getPreferenceAvg()
            );
        }
    }

}
