package ru.cft.shift.util.statistic;

import lombok.Getter;

@Getter
class FloatFullStatistic implements Statistic<Float> {

    private final ShortStatistic<Float> shortStatistic = new ShortStatistic<>();
    private float min;
    private float max;
    private double sum;

    double getAvg() {
        return sum / shortStatistic.getCount();
    }

    @Override
    public void add(Float value) {
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

    private void initialState(Float value) {
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
