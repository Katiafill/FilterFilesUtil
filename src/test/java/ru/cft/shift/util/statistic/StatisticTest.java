package ru.cft.shift.util.statistic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        log.info(statistic.getStatistic());
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
        assertEquals(statistic.getAvg(), sum / list.size());

        log.info(statistic.getStatistic());
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
        assertEquals(statistic.getAvg(), sum / list.size());

        log.info(statistic.getStatistic());
    }

    @Test
    void shortStatisticManager() {
        StatisticManager manager = StatisticManager.create(false);

        manager.add("1");
        manager.add(1);
        manager.add(1.0f);
        manager.add(false);

        log.info(manager.getStatistic());
    }

    @Test
    void fullStatisticManager() {
        StatisticManager manager = StatisticManager.create(true);

        manager.add("1");
        manager.add(1);
        manager.add(1.0f);

        log.info(manager.getStatistic());
    }

    @Test
    void emptyIntegerStatistic() {
        StatisticManager manager = StatisticManager.create(true);

        manager.add("1");
        manager.add(1.0f);

        log.info(manager.getStatistic());
    }
}