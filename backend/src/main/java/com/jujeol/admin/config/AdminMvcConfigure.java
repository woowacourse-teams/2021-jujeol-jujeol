package com.jujeol.admin.config;

import com.jujeol.admin.ui.AdminLoginInterceptor;
import com.jujeol.admin.utils.AdminTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AdminMvcConfigure implements WebMvcConfigurer {

    private final AdminTokenProvider tokenProvider;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }
}
