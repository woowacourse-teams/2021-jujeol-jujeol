package com.jujeol;

import com.jujeol.testtool.NewRequestBuilder;
import com.jujeol.testtool.request.TestAdapterContainer;
import com.jujeol.testtool.util.TestTool;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

//    @PersistenceContext
//    private EntityManager entityManager;

//    @Bean
//    public JPAQueryFactory jpaQueryFactory() {
//        return new JPAQueryFactory(entityManager);
//    }

    @Bean
    public NewRequestBuilder newRequestBuilder(TestAdapterContainer testAdapterContainer) {
        return new NewRequestBuilder(testAdapterContainer, TestTool.REST_ASSURED);
    }
}