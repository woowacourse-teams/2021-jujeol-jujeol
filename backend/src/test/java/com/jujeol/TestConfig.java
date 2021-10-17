package com.jujeol;

import com.jujeol.testtool.RequestBuilder;
import com.jujeol.testtool.request.TestAdapterContainer;
import com.jujeol.testtool.util.TestTool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    @Bean
    public RequestBuilder newRequestBuilder(TestAdapterContainer testAdapterContainer) {
        return new RequestBuilder(testAdapterContainer, TestTool.REST_ASSURED);
    }
}
