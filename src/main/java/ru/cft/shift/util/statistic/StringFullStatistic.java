package ru.cft.shift.util.statistic;

import lombok.Getter;

@Getter
class StringFullStatistic implements Statistic<String> {

    private final ShortStatistic<String> shortStatistic = new ShortStatistic<>();
    private int minLength;
    private int maxLength;

    @Override
    public void add(String value) {
        int len = value.length();
        initialState(len);

        shortStatistic.add(value);

        if(len < minLength) {
            minLength = len;
        }

        if(len > maxLength) {
            maxLength = len;
        }
    }

    private void initialState(int len) {
        if (shortStatistic.isEmpty()) {
            minLength = len;
            maxLength = len;
        }
    }

    @Override
    public String getStatistic() {
        StringBuilder builder = new StringBuilder(100);
        builder.append(shortStatistic.getStatistic());

        if (!shortStatistic.isEmpty()) {
            builder.append(System.lineSeparator());
            builder.append("Min length: ").append(minLength);
            builder.append(System.lineSeparator());
            builder.append("Max length: ").append(maxLength);
        }

        return builder.toString();
    }
}
