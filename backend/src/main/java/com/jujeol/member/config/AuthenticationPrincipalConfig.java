package com.jujeol.member.config;

import com.jujeol.member.ui.interceptor.LoginInterceptor;
import com.jujeol.commons.interceptor.PathMatcherInterceptor;
import com.jujeol.commons.interceptor.PathMethod;
import com.jujeol.member.util.AuthenticationPrincipalArgumentResolver;
import com.jujeol.member.util.JwtTokenProvider;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

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
    }

    private HandlerInterceptor loginInterceptor() {
        final PathMatcherInterceptor interceptor = new PathMatcherInterceptor(new LoginInterceptor(jwtTokenProvider));
        return interceptor
                .excludePathPattern("/**", PathMethod.OPTIONS)

                .includePathPattern("/members/me/**", PathMethod.ANY)

                .includePathPattern("/drinks/*/reviews/**", PathMethod.ANY)
                .excludePathPattern("/drinks/*/reviews", PathMethod.GET);
    }
}
