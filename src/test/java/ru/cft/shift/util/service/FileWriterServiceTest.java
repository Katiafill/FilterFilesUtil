package ru.cft.shift.util.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.cft.shift.util.parameters.UtilParameters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

class FileWriterServiceTest {

    private UtilParameters parameters;

    @BeforeEach
    void setUp() {
        parameters = UtilParameters.builder()
                .path("./out")
                .prefix("sample_")
                .isAppendFile(false)
                .build();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walk(Path.of(parameters.getPath()))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }


    @Test
    void writeIntegers() {
        FileWriterServiceImpl service = new FileWriterServiceImpl(parameters);
        service.writeLine("1", ContentType.INT);
        service.writeLine("10", ContentType.INT);
        service.close();
    }

    @Test
    void writeFloats() {
        FileWriterServiceImpl service = new FileWriterServiceImpl(parameters);
        service.writeLine("1.5", ContentType.FLOAT);
        service.writeLine("-34.7", ContentType.FLOAT);
        service.close();
    }

    @Test
    void writeStrings() {
        FileWriterServiceImpl service = new FileWriterServiceImpl(parameters);
        service.writeLine("Sample", ContentType.STRING);
        service.writeLine("Hello, world!", ContentType.STRING);
        service.close();
    }

    @Test
    void writeAll() {
        FileWriterServiceImpl service = new FileWriterServiceImpl(parameters);
        service.writeLine("19", ContentType.INT);
        service.writeLine("-34.7", ContentType.FLOAT);
        service.writeLine("Hello world!", ContentType.STRING);
        service.close();
    }
}