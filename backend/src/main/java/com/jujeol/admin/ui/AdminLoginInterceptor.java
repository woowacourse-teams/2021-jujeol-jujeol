package com.jujeol.admin.ui;

import com.jujeol.admin.utils.AdminTokenProvider;
import com.jujeol.member.auth.exception.UnauthorizedUserException;
import com.jujeol.member.auth.util.AuthorizationExtractor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class AdminLoginInterceptor implements HandlerInterceptor {

    private final AdminTokenProvider adminTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        final String token = AuthorizationExtractor.extract(request);
        if (!adminTokenProvider.validateToken(token)) {
            throw new UnauthorizedUserException();
        }
        return true;
    }
}
