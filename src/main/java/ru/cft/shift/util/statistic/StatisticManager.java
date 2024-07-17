package ru.cft.shift.util.statistic;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class StatisticManager implements Statistic<Object> {

    private final Statistic<String> stringStatistic;
    private final Statistic<Integer> integerStatistic;
    private final Statistic<Float> floatStatistic;

    private StatisticManager(Statistic<String> stringStatistic,
                             Statistic<Integer> integerStatistic,
                             Statistic<Float> floatStatistic) {
        this.stringStatistic = stringStatistic;
        this.integerStatistic = integerStatistic;
        this.floatStatistic = floatStatistic;
    }

    public static StatisticManager create(boolean full) {
        final Statistic<String> stringStatistic;
        final Statistic<Integer> integerStatistic;
        final Statistic<Float> floatStatistic;

        if (full) {
            stringStatistic = new StringFullStatistic();
            integerStatistic = new IntegerFullStatistic();
            floatStatistic = new FloatFullStatistic();
        } else {
            stringStatistic = new ShortStatistic<>();
            integerStatistic = new ShortStatistic<>();
            floatStatistic = new ShortStatistic<>();
        }

        return new StatisticManager(
                stringStatistic,
                integerStatistic,
                floatStatistic);
    }


    @Override
    public void add(Object value) {
        if(value instanceof String s) {
            stringStatistic.add(s);
        } else if (value instanceof Integer i) {
            integerStatistic.add(i);
        } else if (value instanceof Float f) {
            floatStatistic.add(f);
        } else {
            log.error("Invalid value {}.", value);
        }
    }

    @Override
    public String getStatistic() {
        return "String statistic: " + System.lineSeparator() +
                stringStatistic.getStatistic() + System.lineSeparator() +
                "Integer statistic: " + System.lineSeparator() +
                integerStatistic.getStatistic() + System.lineSeparator() +
                "Float statistic: " + System.lineSeparator() +
                floatStatistic.getStatistic();
    }
}
