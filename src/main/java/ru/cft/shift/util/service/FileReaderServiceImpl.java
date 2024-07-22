package ru.cft.shift.util.service;

import lombok.extern.slf4j.Slf4j;
import ru.cft.shift.util.info.InfoService;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
/* Сервис чтения файлов.
* Файлы читаются последовательно по одной строке
* в порядке, переданном на вход.
*/
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
                FileReader reader = new FileReader(filename);
                readers.add(reader);
            } catch (IOException ex) {
                log.error("Exception occurred while opening file {}", filename, ex);
                InfoService.getInstance().error("Failed open file " + filename + ". It would be skipped.");
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
