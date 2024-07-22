package ru.cft.shift.util.info;

enum ConsoleInfoService implements InfoService {
    INSTANCE;

    @Override
    public void error(String message) {
        System.out.println(message);
    }

    @Override
    public void error(String message, Exception ex) {
        System.out.println(message);
        System.out.println(ex.getLocalizedMessage());
    }

    @Override
    public void result(String result) {
        System.out.println(result);
    }
}
