package com.jujeol.testdatabase;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ProxyConnectionHandler implements InvocationHandler {

    private final QueryCounter queryCounter;
    private Connection connection;

    public ProxyConnectionHandler(Connection connection, QueryCounter queryCounter) {
        this.connection = connection;
        this.queryCounter = queryCounter;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (queryCounter.isCountable()) {
            if (method.getName().equals("prepareStatement")) {
                return getPreparedStatement(method, args);
            }
        }
        return method.invoke(connection, args);
    }

    private PreparedStatement getPreparedStatement(Method method, Object[] args)
            throws IllegalAccessException, InvocationTargetException {
        final PreparedStatement result = (PreparedStatement) method.invoke(connection, args);

        String statement = "";
        for (Object arg : args) {
            if (isQueryStatement(arg)) {
                statement = (String) arg;
                break;
            }
        }
        queryCounter.upCount(statement);
        return result;
    }

    private boolean isQueryStatement(Object statement) {
        if (statement.getClass().isAssignableFrom(String.class)) {
            String queryStatement = (String) statement;
            return (queryStatement.startsWith("select") || queryStatement.startsWith("insert") ||
                    queryStatement.startsWith("update") || queryStatement.startsWith("delete"));
        }
        return false;
    }
}
