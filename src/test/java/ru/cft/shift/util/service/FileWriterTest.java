package ru.cft.shift.util.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileWriterTest {

    private final String filename = "sample.txt";

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(filename));
    }

    @Test
    void writeLine_newFile() throws IOException {
        List<String> lines = List.of("Hello world!", "Second line.");
        writeLines(lines, false);
        checkResult(lines);
    }

    @Test
    void writeLine_overwriteFile() throws IOException {
        List<String> lines = List.of("Hello world!", "Second line.");
        writeLines(lines, false);
        checkResult(lines);

        List<String> lines2 = List.of("New file. New lines.");
        writeLines(lines2, false);
        checkResult(lines2);
    }

    @Test
    void writeLine_appendFile() throws IOException {
        List<String> lines = List.of("Hello world!", "Append line to file.");
        writeLines(lines.subList(0,1), false);
        writeLines(lines.subList(1,2), true);
        checkResult(lines);
    }

    @Test
    void writeLine_appendToEmptyFile() throws IOException {
        List<String> lines = List.of("Hello world!", "Append line to file.");
        writeLines(lines, true);
        checkResult(lines);
    }

    private void writeLines(List<String> lines, boolean isAppend) throws IOException {
        FileWriter writer = new FileWriter(filename, isAppend);
        lines.forEach(writer::writeLine);
        writer.close();
    }

    private void checkResult(List<String> actual) throws IOException {
        List<String> result = Files.readAllLines(Path.of(filename));
        assertEquals(result, actual);
    }
}