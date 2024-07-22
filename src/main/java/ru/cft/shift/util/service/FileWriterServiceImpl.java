package ru.cft.shift.util.service;

import lombok.extern.slf4j.Slf4j;
import ru.cft.shift.util.info.InfoService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

record FileWriterServiceParameters(String path, String prefix, boolean isAppendFile){}

@Slf4j
/* Сервис записи отфильтрованных данных по файлам.
* Запись осуществляется построчно.
* Каждый тип данных записывается в соответствующий файл.
* */
class FileWriterServiceImpl implements FileWriterService {
    private final Map<ContentType, FileWriter> writers;
    private final FileWriterServiceParameters parameters;

    FileWriterServiceImpl(FileWriterServiceParameters parameters) {
        this.parameters = parameters;
        writers = new HashMap<>();
    }

    @Override
    public void close() {
        writers.values().forEach(FileWriter::close);
    }

    @Override
    public void writeLine(String line, ContentType type) {
        FileWriter writer = getFileWriter(type);
        if (writer == null) return;

        writer.writeLine(line);
    }

    private FileWriter getFileWriter(ContentType type) {
        FileWriter writer = writers.get(type);

        if (writer == null) {
            writer = createFileWriter(type);
            writers.put(type, writer);
        }

        return writer;
    }

    private FileWriter createFileWriter(ContentType type) {
        try {
            String filename = getFilenameForType(type);
            return new FileWriter(filename, parameters.isAppendFile());
        } catch (IOException ex) {
            log.error("Exception occurred while opening file {}", type.getFilename(), ex);
            InfoService.getInstance().error("Failed create/open " + type.getFilename() + ".", ex);
            return null;
        }
    }

    private String getFilenameForType(ContentType type) throws IOException {
        StringBuilder builder = new StringBuilder(type.getFilename());

        String prefix = parameters.prefix();
        if (prefix != null) {
            builder.insert(0, prefix);
        }

        String path = parameters.path();
        if (path != null) {
            Files.createDirectories(Path.of(path));
            builder.insert(0, path + "/");
        }

        return builder.toString();
    }
}
