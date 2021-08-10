package com.jujeol.commons.aop;

import com.jujeol.commons.exception.JujeolException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
@Profile("!test")
public class LogAop {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @AfterThrowing(value = "execution(* com.jujeol..*Service.*(..))", throwing = "e")
    public void checkException(JoinPoint joinPoint, Exception e) {
        log.info("예외 발생지점 : {}", joinPoint.getSignature().toString());
        if (e instanceof JujeolException) {
            return;
        }
        log.warn("------------------------- StackTrace Start -------------------------");
        for (StackTraceElement element : e.getStackTrace()) {
            log.warn("{}", element);
        }
        log.warn("------------ StackTrace End ------------");
    }

    @Around(value = "execution(* com.jujeol.drink..*(..))"
            + "|| execution(* com.jujeol.admin..*(..))"
            + "|| execution(* com.jujeol.member..*(..))")
    public Object checkSuccess(ProceedingJoinPoint joinPoint) throws Throwable {
        Object returnValue = joinPoint.proceed();
        List<Object> params = Stream.of(joinPoint.getArgs())
                .map(arg -> {
                    try {
                        return objectMapper.writeValueAsString(arg);
                    } catch (IOException e) {
                        return arg;
                    }
                })
                .collect(Collectors.toList());
        log.info("파라미터 : {}", params);
        log.info("시그니쳐 : {}", joinPoint.getSignature().toString());
        try {
            log.info("반환값 : {}", objectMapper.writeValueAsString(returnValue));
        } catch (Exception e) {
            log.info("반환값 : {}", returnValue);
        }
        return returnValue;
    }

    @Around("@annotation(LogWithTime)")
    public Object checkTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        log.info("{} : {}", joinPoint.getSignature().getName(),
                (endTime - startTime) / 1000.0 + "초");
        return result;
    }
}
