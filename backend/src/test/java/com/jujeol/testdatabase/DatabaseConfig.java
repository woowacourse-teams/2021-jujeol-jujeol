package com.jujeol.testdatabase;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

    @Bean
    public QueryCounter queryCounter() {
        return new QueryCounter();
    }

    @Bean
    public DataSource dataSource() {
        final DataSource dataSource = DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:mem:test")
                .username("SA")
                .password("").build();
        return new TestDataSource(dataSource, queryCounter());
    }
}
