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

    //TODO: p6spy로 쿼리 세팅이 나도도록, 커넥션 풀 사용현황도 로그로 찍힌다.
    //TODO: 모든 패키지 적용 : 쿼리 체크

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @AfterThrowing(value = "execution(* com.jujeol..*Service.*(..))", throwing = "e")
    public void checkException(JoinPoint joinPoint, Exception e) {
        logger.info("========================= LogWithException Start =========================");
        logger.info("예외 발생지점 : {}", joinPoint.getSignature().toString());
        if (e instanceof JujeolException) {
            logger.info("========================= LogWithException End =========================");
            return;
        }
        logger.warn("------------------------- StackTrace Start -------------------------");
        for (StackTraceElement element : e.getStackTrace()) {
            logger.warn("{}", element);
        }
        logger.warn("------------------------- StackTrace End -------------------------");
        logger.info("========================= LogWithException End =========================");
    }

    @AfterReturning(value = "execution(* com.jujeol..*Service.*(..))", returning = "returnValue")
    public void checkSuccess(JoinPoint joinPoint, Object returnValue) {
        logger.info("========================= LogSuccess Start =========================");
        logger.info("파라미터 : {}", Arrays.toString(joinPoint.getArgs()));
        logger.info("시그니쳐 : {}", joinPoint.getSignature().toString());
        logger.info("반환값 : {}", returnValue.toString());
        logger.info("========================= LogSuccess End =========================");
    }

    @Around("@annotation(LogWithTime)")
    public Object checkTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        logger.info("========================= LogWithTime Start =========================");
        logger.info("{} : {}", joinPoint.getSignature().getName(),
                (endTime - startTime) / 1000.0 + "초");
        logger.info("========================= LogWithTime End =========================");
        return result;
    }
}
