package com.jujeol.testdatabase;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DatasourceAop {

    private final QueryCounter queryCounter;

    public DatasourceAop(QueryCounter queryCounter) {
        this.queryCounter = queryCounter;
    }

    @Around("execution(* javax.sql.DataSource.getConnection())")
    public Object datasource(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final Connection connection = (Connection) proceedingJoinPoint.proceed();
        return Proxy.newProxyInstance(
                connection.getClass().getClassLoader(),
                connection.getClass().getInterfaces(),
                new ProxyConnectionHandler(connection, queryCounter));
    }
}
