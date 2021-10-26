package com.jujeol.admin.config;

import com.jujeol.admin.ui.AdminLoginInterceptor;
import com.jujeol.admin.utils.AdminTokenProvider;
import com.jujeol.commons.interceptor.PathMatcherInterceptor;
import com.jujeol.commons.interceptor.PathMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AdminMvcConfigure implements WebMvcConfigurer {

    private final AdminTokenProvider tokenProvider;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminLoginInterceptor());
    }

    private HandlerInterceptor adminLoginInterceptor() {
        final PathMatcherInterceptor interceptor = new PathMatcherInterceptor(
                new AdminLoginInterceptor(tokenProvider));
        return interceptor
                .excludePathPattern("/**", PathMethod.OPTIONS)
                .excludePathPattern("/admin/login", PathMethod.POST)
                .excludePathPattern("/admin/login", PathMethod.GET)
                .excludePathPattern("/admin/admin/html/login.html", PathMethod.ANY)

                .includePathPattern("/admin/**", PathMethod.ANY);
    }
}
