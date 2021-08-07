package com.jujeol.testdatabase;

public class Count {

    private long value;

    public Count(long value) {
        this.value = value;
    }

    public Count upCount() {
        return new Count(++value);
    }

    public long value() {
        return value;
    }
}

