package com.jujeol.drink.domain.repository2;

import com.jujeol.drink.domain.Drink;
import java.util.List;
import javax.sql.DataSource;
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
        final SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(drinks);
        jdbcInsert.executeBatch(batch);
    }
}