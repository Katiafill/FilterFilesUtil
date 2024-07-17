package ru.cft.shift.util.statistic;

import lombok.Getter;

@Getter
class IntegerFullStatistic implements Statistic<Integer> {

    private final ShortStatistic<Integer> shortStatistic = new ShortStatistic<>();
    private int min;
    private int max;
    private long sum;

    long getAvg() {
        return sum / shortStatistic.getCount();
    }

    @Override
    public void add(Integer value) {
        initialState(value);

        shortStatistic.add(value);

        if (value < min) {
            min = value;
        }

        if (value > max) {
            max = value;
        }

        sum += value;
    }

    private void initialState(Integer value) {
        if (shortStatistic.isEmpty()) {
            min = value;
            max = value;
            sum = 0;
        }
    }

    @Override
    public String getStatistic() {
        StringBuilder builder = new StringBuilder(100);
        builder.append(shortStatistic.getStatistic());

        if (!shortStatistic.isEmpty()) {
            builder.append(System.lineSeparator());
            builder.append("Min: ").append(min);
            builder.append(System.lineSeparator());
            builder.append("Max: ").append(max);
            builder.append(System.lineSeparator());
            builder.append("Sum: ").append(sum);
            builder.append(System.lineSeparator());
            builder.append("Avg: ").append(getAvg());
        }

        return builder.toString();
    }
}
