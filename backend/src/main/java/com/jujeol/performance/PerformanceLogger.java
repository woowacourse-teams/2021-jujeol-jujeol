package com.jujeol.performance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Proxy;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@RequiredArgsConstructor
public class PerformanceLogger {

    private static final Logger log = LoggerFactory.getLogger("PERFORMANCE");

    private final ObjectMapper objectMapper;
    private ThreadLocal<PerformanceLoggingForm> logForm;

    @Before("@within(org.springframework.transaction.annotation.Transactional) || @annotation(org.springframework.transaction.annotation.Transactional)")
    public void beforeTransaction() {
        final long startTransactionTime = System.currentTimeMillis();
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
            getLoggingForm().setTargetApi(request.getRequestURI());
        } catch (IllegalStateException e) {
            getLoggingForm().setTargetApi("dataLoader");
        }
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {

            @Override
            public void afterCompletion(int status) {
                try {
                    getLoggingForm()
                        .setTransactionTime(System.currentTimeMillis() - startTransactionTime);

                    log.info(objectMapper.writeValueAsString(getLoggingForm()));
                } catch (JsonProcessingException ignored) {
                } finally {
                    logForm.remove();
                }
            }
        });

    }

    @Around("execution(* javax.sql.DataSource.getConnection())")
    public Object datasource(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final Object proceed = proceedingJoinPoint.proceed();
        return Proxy.newProxyInstance(
            proceed.getClass().getClassLoader(),
            proceed.getClass().getInterfaces(),
            new ProxyConnectionHandler(proceed, getLoggingForm()));
    }

    private PerformanceLoggingForm getLoggingForm() {
        if (logForm == null) {
            logForm = new ThreadLocal<>();
        }

        if (logForm.get() == null) {
            logForm.set(new PerformanceLoggingForm());
        }
        return logForm.get();
    }
}
