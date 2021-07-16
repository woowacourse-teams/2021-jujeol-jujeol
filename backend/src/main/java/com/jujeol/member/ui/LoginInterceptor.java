package com.jujeol.member.ui;

import com.jujeol.member.exception.UnauthorizedUserException;
import com.jujeol.member.util.AuthorizationExtractor;
import com.jujeol.member.util.JwtTokenProvider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@AllArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) {

        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            return true;
        }

        String token = AuthorizationExtractor.extract(request);
        if (jwtTokenProvider.validateToken(token)) {
            return true;
        }
        throw new UnauthorizedUserException();
    }
}
