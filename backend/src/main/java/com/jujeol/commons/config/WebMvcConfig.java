package com.jujeol.commons.config;

import com.jujeol.commons.interceptor.LogInterceptor;
import com.jujeol.commons.interceptor.PathMatcherInterceptor;
import com.jujeol.commons.interceptor.PathMethod;
import com.jujeol.member.auth.ui.interceptor.LoginInterceptor;
import com.jujeol.member.auth.util.AuthenticationPrincipalArgumentResolver;
import com.jujeol.member.auth.util.JwtTokenProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*").allowedOriginPatterns("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/docs/**")
                .addResourceLocations("classpath:/static/");

        registry.addResourceHandler("/admin/**")
                .addResourceLocations("classpath:/static/admin/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin").setViewName("/admin/html/index.html");

        registry.addRedirectViewController("/", "/docs/index.html");
    }

    @Override
    public void addArgumentResolvers(List argumentResolvers) {
        argumentResolvers.add(createAuthenticationPrincipalArgumentResolver());
    }

    @Bean
    public AuthenticationPrincipalArgumentResolver createAuthenticationPrincipalArgumentResolver() {
        return new AuthenticationPrincipalArgumentResolver(jwtTokenProvider);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor());
        registry.addInterceptor(new LogInterceptor());
    }

    @Bean
    public HandlerInterceptor loginInterceptor() {
        final PathMatcherInterceptor interceptor = new PathMatcherInterceptor(new LoginInterceptor(jwtTokenProvider));
        return interceptor
                .excludePathPattern("/**", PathMethod.OPTIONS)

                .includePathPattern("/members/me/**", PathMethod.ANY)

                .includePathPattern("/drinks/*/reviews/**", PathMethod.ANY)
                .excludePathPattern("/drinks/*/reviews", PathMethod.GET);
    }
}
