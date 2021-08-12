package com.jujeol.member.auth.ui.interceptor;

import com.jujeol.member.auth.exception.UnauthorizedUserException;
import com.jujeol.member.auth.util.AuthorizationExtractor;
import com.jujeol.member.auth.util.JwtTokenProvider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) {

        String token = AuthorizationExtractor.extract(request);
        if (jwtTokenProvider.validateToken(token)) {
            return true;
        }
        throw new UnauthorizedUserException();
    }
}
