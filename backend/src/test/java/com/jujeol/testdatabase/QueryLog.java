package com.jujeol.testdatabase;

public class QueryLog {

    private long count;
    private String statement;

    public QueryLog(long count, String statement) {
        this.count = count;
        this.statement = statement;
    }

    public long getCount() {
        return count;
    }

    public String getStatement() {
        return statement;
    }
}
