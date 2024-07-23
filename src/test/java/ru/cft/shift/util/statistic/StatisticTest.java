package ru.cft.shift.util.statistic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.cft.shift.util.statistic.StatisticHelper.*;

@Slf4j
class StatisticTest {

    @Test
    void integerShortStatistic() {
        ShortStatistic<Integer> shortStatistic = new ShortStatistic<>();

        List<Integer> list = List.of(1, 2, 3, 4, 5);
        checkShortStatistic(list, shortStatistic);
    }

    @Test
    void stringShortStatistic() {
        ShortStatistic<String> shortStatistic = new ShortStatistic<>();
        List<String> list = List.of("1", "2", "3", "4", "5");
        checkShortStatistic(list, shortStatistic);
    }

    private <T> void checkShortStatistic(List<T> list, ShortStatistic<T> shortStatistic) {
        list.forEach(shortStatistic::add);

        assertEquals(shortStatistic.getCount(), list.size());
        assertEquals(shortStatistic.getStatistic(), "Count: " + list.size());
    }

    @Test
    void stringFullStatistic() {
        StringFullStatistic statistic = new StringFullStatistic();
        List<String> list = List.of("1", "20", "3000", "400", "50000");

        list.forEach(statistic::add);

        assertEquals(statistic.getShortStatistic().getCount(), list.size());
        assertEquals(statistic.getMinLength(), 1);
        assertEquals(statistic.getMaxLength(), 5);
        assertEquals(statistic.getStatistic(),
                createFullStringStatistic(list.size(), 1, 5));
    }

    @Test
    void integerFullStatistic() {
        IntegerFullStatistic statistic = new IntegerFullStatistic();
        List<Integer> list = List.of(-1, 4, 7967, 20, -300);

        list.forEach(statistic::add);

        assertEquals(statistic.getShortStatistic().getCount(), list.size());
        assertEquals(statistic.getMin(), -300);
        assertEquals(statistic.getMax(), 7967);
        long sum = list.stream().mapToInt(Integer::intValue).sum();
        assertEquals(statistic.getSum(), sum);
        long avg = sum / list.size();
        assertEquals(statistic.getAvg(), avg);

        assertEquals(statistic.getStatistic(),
                createFullIntegerStatistic(list.size(), -300, 7967, sum, avg));
    }

    @Test
    void floatFullStatistic() {
        FloatFullStatistic statistic = new FloatFullStatistic();
        List<Float> list = List.of(-1.0f, 4.5f, 7967.0f, 20.5f, -300.3f);

        list.forEach(statistic::add);

        assertEquals(statistic.getShortStatistic().getCount(), list.size());
        assertEquals(statistic.getMin(), -300.3f);
        assertEquals(statistic.getMax(), 7967.0f);
        double sum = list.stream().mapToDouble(Float::doubleValue).sum();
        assertEquals(statistic.getSum(), sum);
        double avg = sum / list.size();
        assertEquals(statistic.getAvg(), avg);

        assertEquals(statistic.getStatistic(),
                createFullFloatStatistic(list.size(), -300.3f, 7967.0f, sum, avg));
    }

    @Test
    void shortStatisticManager() {
        StatisticManager manager = StatisticManager.create(false);

        manager.add("sample");
        manager.add(1);
        manager.add(1.0f);
        manager.add(false);

        assertEquals(manager.getStatistic(),
                createStatistic(
                        createShortStatistic(1),
                        createShortStatistic(1),
                        createShortStatistic(1)
                ));
    }

    @Test
    void fullStatisticManager() {
        StatisticManager manager = StatisticManager.create(true);

        manager.add("sample");
        manager.add(1);
        manager.add(1.0f);

        assertEquals(manager.getStatistic(),
                createStatistic(
                        createFullStringStatistic(1, 6,6),
                        createFullIntegerStatistic(1, 1, 1, 1, 1),
                        createFullFloatStatistic(1, 1.0f, 1.0f, 1.0f, 1.0f)
                ));
    }

    @Test
    void emptyIntegerStatistic() {
        StatisticManager manager = StatisticManager.create(true);

        manager.add("sample");
        manager.add(1.0f);

        assertEquals(manager.getStatistic(),
                createStatistic(
                        createFullStringStatistic(1, 6,6),
                        createShortStatistic(0),
                        createFullFloatStatistic(1, 1.0f, 1.0f, 1.0f, 1.0f)
                ));
    }

}