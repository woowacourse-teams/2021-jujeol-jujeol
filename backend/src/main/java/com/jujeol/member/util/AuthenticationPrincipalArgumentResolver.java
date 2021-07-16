package com.jujeol.member.util;

import com.jujeol.member.domain.AuthenticationPrincipal;
import com.jujeol.member.domain.LoginMember;
import com.jujeol.member.exception.InvalidTokenException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationPrincipalArgumentResolver(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String credentials = AuthorizationExtractor
                .extract(webRequest.getNativeRequest(HttpServletRequest.class));
        if (!jwtTokenProvider.validateToken(credentials)) {
            throw new InvalidTokenException();
        }

        Long id = Long.parseLong(jwtTokenProvider.getPayload(credentials));

        return new LoginMember(id);
    }
}
