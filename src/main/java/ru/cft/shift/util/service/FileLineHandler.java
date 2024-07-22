package ru.cft.shift.util.service;

import lombok.Getter;
import ru.cft.shift.util.statistic.StatisticManager;

@Getter
class FileLineHandler {
    private final StatisticManager manager;

    public FileLineHandler(boolean isFullStatistic) {
        manager = StatisticManager.create(isFullStatistic);
    }

    public ContentType parse(String line) {
        // Удалим все пробелы из строки, чтобы было возможно парсить числа.
        line = line.strip();

        Integer intValue = parseInteger(line);
        if (intValue != null) {
            manager.add(intValue);
            return ContentType.INT;
        }

        Float floatValue = parseFloat(line);
        if (floatValue != null) {
            manager.add(floatValue);
            return ContentType.FLOAT;
        }

        manager.add(line);
        return ContentType.STRING;
    }

    private Integer parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Float parseFloat(String value) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
