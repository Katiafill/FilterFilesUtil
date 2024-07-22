package ru.cft.shift.util.service;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {
    private final static String filename = "sample.txt";

    @BeforeEach
    void setUp() throws IOException {
        Files.createFile(Path.of(filename));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(filename));
    }

    @Test
    void readLine() throws IOException {
        List<String> lines = List.of("10", "Hello world!", "123.00");
        Files.write(Path.of(filename), lines);

        FileReader fileReader = new FileReader(filename);
        List<String> result = new ArrayList<>();
        while(!fileReader.isFinished()) {
            String s = fileReader.readLine();
            if (s != null) {
                result.add(s);
            }
        }
        fileReader.close();

        assertEquals(result, lines);
    }

    @Test
    void readLine_emptyFile() throws IOException {
        FileReader fileReader = new FileReader(filename);
        String s;
        List<String> result = new ArrayList<>();
        while(!fileReader.isFinished()) {
            s = fileReader.readLine();
            if (s != null) {
                result.add(s);
            }
        }
        fileReader.close();

        assertEquals(result, List.of());
    }

    @Test
    void noFile() {
        assertThrows(IOException.class, () -> {
           FileReader reader = new FileReader("nofile.txt");
        });
    }
}