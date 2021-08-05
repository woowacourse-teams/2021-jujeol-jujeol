package com.jujeol.commons.aop;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAop {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @AfterThrowing("@annotation(LogWithException)")
    public void checkException(JoinPoint joinPoint) {
        logger.warn("예외 발생지점 : {}", joinPoint.getSignature().toString());
    }

    @AfterReturning(value = "@annotation(LogWithSuccess)", returning = "returnValue")
    public void checkSuccess(JoinPoint joinPoint, Object returnValue) {
        logger.info("파라미터 : {}", Arrays.toString(joinPoint.getArgs()));
        logger.info("시그니쳐 : {}", joinPoint.getSignature().toString());
        logger.info("반환값 : {}", returnValue.toString());
    }

    @Around("@annotation(LogWithTime)")
    public Object checkTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        logger.info("{} : {}", joinPoint.getSignature().getName(),
                (endTime - startTime) / 1000.0 + "초");

        return result;
    }
}
