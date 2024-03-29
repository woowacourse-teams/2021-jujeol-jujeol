package com.jujeol.member.interceptor;

import com.jujeol.exception.ExceptionCodeAndDetails;
import com.jujeol.exception.JujeolBadRequestException;
import com.jujeol.member.domain.model.TokenProvider;
import com.jujeol.utils.AuthorizationExtractor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String credentials = AuthorizationExtractor.extract(request);
        if (credentials == null) {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }

        if (!tokenProvider.validateToken(credentials)) {
            throw new JujeolBadRequestException(ExceptionCodeAndDetails.INVALID_TOKEN);
        }
        Long memberId = Long.parseLong(tokenProvider.getPayload(credentials));
        AuthHttpServletRequest authHttpServletRequest = new AuthHttpServletRequest(request);
        authHttpServletRequest.putHeader("memberId", String.valueOf(memberId));
        return HandlerInterceptor.super.preHandle(authHttpServletRequest, response, handler);
    }
}
