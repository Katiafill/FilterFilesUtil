package ru.cft.shift.util.service;

import java.util.function.Consumer;

public interface FileReaderService extends AutoCloseable {
    void readFiles(Consumer<String> consumer);
}
