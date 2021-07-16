package com.jujeol.member.util;

import com.jujeol.member.ui.AuthenticationPrincipal;
import com.jujeol.member.ui.Authority;
import com.jujeol.member.ui.LoginMember;
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
            return new LoginMember(Authority.ANONYMOUS);
        }

        Long id = Long.parseLong(jwtTokenProvider.getPayload(credentials));
        return new LoginMember(id, Authority.USER);
    }
}
