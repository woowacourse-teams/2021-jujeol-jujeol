package com.jujeol.testdatabase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerPrinter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerPrinter.class);

    public static void executeQuery() {
        LOGGER.info("===============QUERY EXECUTE===============");
    }

    public static void countQuery(long count) {
        LOGGER.info("Query Count : {}", count);
    }

    public static void printQuery(String statement) {
        LOGGER.info("Query Statement : {}", statement);

    }
}
