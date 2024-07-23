package ru.cft.shift.util.statistic;

public class StatisticHelper {
    public static String createFullStringStatistic(int count, int min, int max) {
        return "Count: " + count +
                System.lineSeparator() +
                "Min length: " + min +
                System.lineSeparator() +
                "Max length: " + max;
    }

    public static String createFullIntegerStatistic(int count, int min, int max, long sum, long avg) {
        return "Count: " + count +
                System.lineSeparator() +
                "Min: " + min +
                System.lineSeparator() +
                "Max: " + max +
                System.lineSeparator() +
                "Sum: " + sum +
                System.lineSeparator() +
                "Avg: " + avg;
    }

    public static String createFullFloatStatistic(int count, float min, float max, double sum, double avg) {
        return "Count: " + count +
                System.lineSeparator() +
                "Min: " + min +
                System.lineSeparator() +
                "Max: " + max +
                System.lineSeparator() +
                "Sum: " + sum +
                System.lineSeparator() +
                "Avg: " + avg;
    }

    public static String createStatistic(String s, String i, String f) {
        return "String statistic: " + System.lineSeparator() +
                s + System.lineSeparator() +
                "Integer statistic: " + System.lineSeparator() +
                i + System.lineSeparator() +
                "Float statistic: " + System.lineSeparator() +
                f;
    }

    public static String createShortStatistic(int count) {
        return "Count: " + count;
    }
}
