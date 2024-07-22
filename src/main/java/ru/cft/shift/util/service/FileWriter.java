package ru.cft.shift.util.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
class FileWriter {
    private final BufferedWriter writer;

    @Getter
    private final String filename;
    @Getter
    private boolean isClosed;
    // Нужно ли добавлять перевод на новую строку.
    private boolean addNewLine;

    FileWriter(String filename, boolean isAppend) throws IOException {
        this.filename = filename;
        writer = getFileWriter(filename, isAppend);
        // Если в файл дописываем, то сразу нужно добавить перевод на новую строку.
        addNewLine = isAppend && isNotEmptyFile(filename);
    }

    void writeLine(String line) {
        if (isClosed) {
            return;
        }

        try {
            if (addNewLine) {
                writer.newLine();
            }
            writer.write(line);
            addNewLine = true;
        } catch (IOException ex) {
            log.error("Exception occurred while writing line {} in file {}", line, filename, ex);
            // Решено, что при ошибке записи в файл, файл закрывается.
            // И больше в него ничего не пытаемся писать.
            close();
        }
    }

    void close() {
        if(isClosed) {
            return;
        }

        try {
            isClosed = true;
            writer.close();
        } catch (IOException ex) {
            log.error("Exception occurred while closing {}", filename, ex );
        }
    }

    private static BufferedWriter getFileWriter(String filename, boolean isAppend) throws IOException {
        return new BufferedWriter(new java.io.FileWriter(filename, isAppend));
    }

    private static boolean isNotEmptyFile(String filename) throws IOException {
        return Files.size(Path.of(filename)) > 0;
    }

}
