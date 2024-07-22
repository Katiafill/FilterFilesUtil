package ru.cft.shift.util.service;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
class FileReaderServiceImpl implements FileReaderService {
    private final List<FileReader> readers;

    FileReaderServiceImpl(List<String> filenames) {
        this.readers = getFileReaders(filenames);
    }

    @Override
    public void readFiles(Consumer<String> consumer) {
        // Построчно читаем все файлы.
        // Файл читается, если он еще не завершен.
        while (isNotAllReadersFinished(readers)) {
            for (FileReader reader : readers) {
                String line = reader.readLine();
                if (line != null) {
                    consumer.accept(line);
                }
            }
        }
    }

    @Override
    public void close() {
        readers.forEach(FileReader::close);
    }

    private List<FileReader> getFileReaders(List<String> filenames) {
        List<FileReader> readers = new ArrayList<>(filenames.size());
        for (String filename : filenames) {
            try {
                readers.add(new FileReader(filename));
            } catch (IOException ex) {
                log.error("Failed open file {}. It would be skipped.", filename, ex);
            }
        }

        return readers;
    }

    private boolean isNotAllReadersFinished(List<FileReader> readers) {
        return readers.stream()
                .filter(FileReader::isFinished)
                .count() != readers.size();
    }
}
