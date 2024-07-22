package ru.cft.shift.util.service;

public interface FileWriterService extends AutoCloseable {
    void writeLine(String line, ContentType type);
}
