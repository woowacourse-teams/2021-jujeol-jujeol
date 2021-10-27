package com.jujeol.drink.recommend.infrastructure.slope;

public class ItemCounter {

    private double sum = 0.0;
    private long count = 0L;

    public void addSum(double sum) {
        ++this.count;
        this.sum += sum;
    }

    public double getDeviation() {
        try {
            return sum / count;
        } catch (ArithmeticException e) {
            return 0;
        }
    }

    public long getCount() {
        return count;
    }
}
