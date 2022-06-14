package com.jujeol.member.domain;

import com.jujeol.member.domain.model.CheersExpressionProvider;
import com.jujeol.member.domain.model.TokenProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @ConditionalOnMissingBean
    @Bean
    public TokenProvider tokenProvider() {
        return new JwtTokenProvider();
    }

    @ConditionalOnMissingBean
    @Bean
    public CheersExpressionProvider cheersExpressionProvider() {
        return new RandomCheersExpressionProvider();
    }
}
