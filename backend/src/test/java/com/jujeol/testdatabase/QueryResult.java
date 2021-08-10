package com.jujeol.testdatabase;

import java.util.ArrayList;
import java.util.List;

public class QueryResult {
    private List<QueryLog> queryLogs;

    public QueryResult() {
        queryLogs = new ArrayList<>();
    }

    public void save(Count upCount, String statement) {
        queryLogs.add(new QueryLog(upCount.value(), statement));
    }

    public QueryResult printLog() {
        queryLogs.forEach(queryLog -> {
            LoggerPrinter.executeQuery();
            LoggerPrinter.countQuery(queryLog.getCount());
            LoggerPrinter.printQuery(queryLog.getStatement());
            LoggerPrinter.executeQuery();
        });
        return this;
    }

    public long queryCount() {
        return queryLogs.stream().mapToLong(QueryLog::getCount).max().orElse(0);
    }
}
