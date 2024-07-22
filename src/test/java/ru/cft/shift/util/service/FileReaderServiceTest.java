package ru.cft.shift.util.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderServiceTest {

    @Test
    void readFiles_oneFile() throws IOException {
        String filename = "sample.txt";
        List<String> lines = List.of("Hello world!", "123", "234.6");
        createFile(filename, lines);

        List<String> result = new ArrayList<>();
        FileReaderServiceImpl service = new FileReaderServiceImpl(List.of(filename));
        service.readFiles(result::add);

        service.close();
        deleteFile(filename);

        assertEquals(result, lines);
    }

    @Test
    void readFiles_twoFiles() throws IOException {
        String filename1 = "sample.txt";
        String filename2 = "sample2.txt";
        List<String> lines1 = List.of("Hello world!", "123", "234.6");
        List<String> lines2 = List.of("Sample text", "-123", "-234.6");
        createFile(filename1, lines1);
        createFile(filename2, lines2);
        List<String> lines = List.of(
                "Hello world!",
                "Sample text",
                "123",
                "-123",
                "234.6",
                "-234.6"
        );

        List<String> result = new ArrayList<>();
        FileReaderServiceImpl service = new FileReaderServiceImpl(
                List.of(filename1, filename2));
        service.readFiles(result::add);

        service.close();
        deleteFile(filename1);
        deleteFile(filename2);

        assertEquals(result, lines);
    }

    private void createFile(String file, List<String> content) throws IOException {
        Path path = Files.createFile(Path.of(file));
        Files.write(path, content);
    }

    @SneakyThrows
    private void deleteFile(String file) {
        Files.delete(Path.of(file));
    }
}