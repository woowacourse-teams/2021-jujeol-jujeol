package com.jujeol.drink.recommend.infrastructure.slope;

public class ItemCounter {

    private double sum = 0.0;
    private long count = 0L;

    public void addSum(double sum) {
        ++this.count;
        this.sum += sum;
    }

    public double getDeviation() {
        return sum / count;
    }

    public long getCount() {
        return count;
    }

    public void minusSum(double preference) {
        --this.count;
        this.sum -= preference;
    }

    public void updateSum(double preference) {
        this.sum += preference;
    }
}
