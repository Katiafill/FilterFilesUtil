package ru.cft.shift.util.statistic;

import lombok.Getter;

@Getter
class ShortStatistic<T> implements Statistic<T> {
    private int count;

    @Override
    public void add(T value) {
        count++;
    }

    @Override
    public String getStatistic() {
        return "Count: " + count;
    }

    boolean isEmpty() {
        return getCount() == 0;
    }

}
