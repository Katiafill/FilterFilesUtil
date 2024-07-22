package ru.cft.shift.util.service;

import lombok.extern.slf4j.Slf4j;
import ru.cft.shift.util.parameters.UtilParameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Slf4j
class FileWriterService {
    private final Map<ContentType, FileWriter> writers;
    private final UtilParameters parameters;

    FileWriterService(UtilParameters parameters) {
        this.parameters = parameters;
        writers = new HashMap<>();
    }

    void closeAll() {
        writers.values().forEach(FileWriter::close);
    }

    void writeLine(String line, ContentType type) {
        FileWriter writer = getFileWriter(type);
        if (writer == null) return;

        writer.writeLine(line);
    }

    private FileWriter getFileWriter(ContentType type) {
        FileWriter writer = writers.get(type);

        if (writer == null) {
            writer = createFileWriter(type);
        }

        return writer;
    }

    private FileWriter createFileWriter(ContentType type) {
        try {
            String filename = getFilenameForType(type);
            FileWriter writer = new FileWriter(filename, parameters.isAppendFile());
            writers.put(type, writer);
            return writer;
        } catch (IOException ex) {
            log.error("Exception occurred while opening file {}", type.getFilename(), ex);
            return null;
        }
    }

    private String getFilenameForType(ContentType type) throws IOException {
        StringBuilder builder = new StringBuilder(type.getFilename());

        String prefix = parameters.getPrefix();
        if (prefix != null) {
            builder.insert(0, prefix);
        }

        String path = parameters.getPath();
        if (path != null) {
            Files.createDirectories(Path.of(path));
            builder.insert(0, path + "/");
        }

        return builder.toString();
    }
}
