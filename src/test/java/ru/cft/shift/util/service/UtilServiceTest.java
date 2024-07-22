package ru.cft.shift.util.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.cft.shift.util.parameters.UtilParameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class UtilServiceTest {

    @Test
    void run() throws IOException {
        String filename = "sample.txt";
        UtilParameters parameters = UtilParameters.builder()
                .path("./out")
                .prefix("test_")
                .files(List.of(filename))
                .isFullStatistic(true)
                .build();

        createFile(filename, List.of("Hello world", "123", "123.00"));

        UtilService service = new UtilService(parameters);
        String result = service.run();

        deleteFile(filename);
        assertNotNull(result);
        log.info(result);
    }

    private void createFile(String file, List<String> content) throws IOException {
        Path path = Files.createFile(Path.of(file));
        Files.write(path, content);
    }

    private void deleteFile(String file) throws IOException {
        Files.delete(Path.of(file));
    }
}