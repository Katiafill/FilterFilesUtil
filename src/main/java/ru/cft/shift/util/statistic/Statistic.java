package ru.cft.shift.util.statistic;

// Интерфейс для сбора статистики.
public interface Statistic<T> {
    // Добавить значение.
    void add(T value);
    // Получить статистику.
    String getStatistic();
}
