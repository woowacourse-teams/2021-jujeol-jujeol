package com.jujeol.testdatabase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

    @Bean
    public QueryCounter queryCounter() {
        return new QueryCounter();
    }
}
