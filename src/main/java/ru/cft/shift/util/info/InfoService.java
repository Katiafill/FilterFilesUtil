package ru.cft.shift.util.info;

// Сервис для централизованного информирования
// о результате работы утилиты.
public interface InfoService {
    void error(String message);
    void error(String message, Exception ex);
    void result(String result);

    static InfoService getInstance() {
        return ConsoleInfoService.INSTANCE;
    }
}
