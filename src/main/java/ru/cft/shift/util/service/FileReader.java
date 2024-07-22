package ru.cft.shift.util.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
class FileReader {

    private final BufferedReader reader;

    @Getter
    private final String filename;
    @Getter
    private boolean isFinished;

    FileReader(String filename) throws IOException {
        this.filename = filename;
        reader = getFileReader(filename);
    }

    String readLine() {
        if (isFinished) {
            return null;
        }

        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException ex) {
            log.error("Exception occurred while reading line in file {}", filename, ex);
        }

        // Закончится обработка с файлом как при конце файла,
        // так и при ошибке чтения файла.
        if (line == null) {
            isFinished = true;
        }

        return line;
    }

    void close() {
        try {
            reader.close();
        } catch (IOException ex) {
            log.error("Exception occurred while closing {}", filename, ex );
        }
    }

    private static BufferedReader getFileReader(String filename) throws IOException {
        Path path = Path.of(filename);
        return Files.newBufferedReader(path);
    }
}
