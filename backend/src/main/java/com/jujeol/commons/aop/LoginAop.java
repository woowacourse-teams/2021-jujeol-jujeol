package com.jujeol.commons.aop;

import com.jujeol.member.auth.exception.UnauthorizedUserException;
import com.jujeol.member.auth.ui.LoginMember;
import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginAop {

        @Before("@within(com.jujeol.commons.aop.MemberOnly) || @annotation(com.jujeol.commons.aop.MemberOnly)")
        public void checkLoginMember(JoinPoint joinPoint) {
            final LoginMember loginMember = (LoginMember) Arrays.stream(joinPoint.getArgs())
                    .filter(argument -> argument instanceof LoginMember)
                    .findAny()
                    .orElseThrow(() -> new IllegalStateException("LoginMember 가 존재하지 않습니다."));

            loginMember.act().throwIfAnonymous(UnauthorizedUserException::new);
        }

}
