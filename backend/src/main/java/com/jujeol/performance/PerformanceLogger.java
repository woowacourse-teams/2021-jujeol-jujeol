package com.jujeol.performance;

import java.lang.reflect.Proxy;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.support.ScopeNotActiveException;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class PerformanceLogger {

    private final RequestApiExtractor requestApiExtractor;
    private final PerformanceLoggingForm logForm;

    @Before("@within(org.springframework.web.bind.annotation.RestController) || @annotation(org.springframework.web.bind.annotation.RestController)")
    public void beforeController(JoinPoint joinPoint) {
        try {
            final String test = logForm.toString();
            final RequestApi requestApi = requestApiExtractor.extractRequestApi(joinPoint);

            logForm.setTargetApi(requestApi.getUrlForm());
            logForm.setTargetMethod(requestApi.getMethod());
        } catch (ScopeNotActiveException ignored) {
        }
    }


    @Around("execution(* javax.sql.DataSource.getConnection())")
    public Object datasource(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final Object proceed = proceedingJoinPoint.proceed();

        try {
            final String test = logForm.toString();
            return Proxy.newProxyInstance(
                    proceed.getClass().getClassLoader(),
                    proceed.getClass().getInterfaces(),
                    new ProxyConnectionHandler(proceed, logForm));
        } catch (ScopeNotActiveException e) {
            return proceed;
        }
    }
}
