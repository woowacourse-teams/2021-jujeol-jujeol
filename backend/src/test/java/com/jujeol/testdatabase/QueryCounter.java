package com.jujeol.testdatabase;


public class QueryCounter {

    private QueryResult queryResult;
    private Count count;
    private boolean countable;

    public QueryCounter() {
        this.count = new Count(0);
        this.queryResult = new QueryResult();
        this.countable = false;
    }

    public void startCount() {
        this.countable = true;
        this.count = new Count(0);
        this.queryResult = new QueryResult();
    }

    public void upCount(String statement) {
        this.count = count.upCount();
        this.queryResult.save(count, statement);
    }

    public QueryResult endCount() {
        final QueryResult result = this.queryResult;
        this.queryResult = new QueryResult();
        this.count = new Count(0);
        this.countable = false;
        return result;
    }

    public boolean isCountable() {
        return countable;
    }
}
