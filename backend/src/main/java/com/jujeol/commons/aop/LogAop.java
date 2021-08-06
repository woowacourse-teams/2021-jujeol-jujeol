package com.jujeol.commons.aop;

import com.jujeol.commons.exception.JujeolException;
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

    //TODO: start와 end 격리 필요, 각각의 로그를 쉽게 분리하여 보고 싶다.
    //TODO: 그리고 start, end할 때 쓰레드도 표시 해주라 Thread.currentThread().getName()
    //TODO: p6spy로 쿼리 세팅이 나도도록, 커넥션 풀 사용현황도 로그로 찍힌다.
    //TODO: 모든 패키지 적용 : 정상작동, 에러 발생 시, 쿼리 체크 / 어노테이션 : 시간 측정

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @AfterThrowing(value = "@annotation(LogWithException)", throwing = "e")
    public void checkException(JoinPoint joinPoint, Exception e) {
        logger.info("예외 발생지점 : {}", joinPoint.getSignature().toString());
        if (e instanceof JujeolException) {
            return;
        }
        logger.warn("========================= StackTrace Start ========================");
        for (StackTraceElement element : e.getStackTrace()) {
            logger.warn("{}", element);
        }
        logger.warn("========================= StackTrace End ========================");
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
