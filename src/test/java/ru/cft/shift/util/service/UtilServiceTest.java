package ru.cft.shift.util.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.cft.shift.util.parameters.UtilParameters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.cft.shift.util.statistic.StatisticHelper.*;
import static ru.cft.shift.util.statistic.StatisticHelper.createFullFloatStatistic;

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

        deleteFile(filename, parameters.getPath());
        assertNotNull(result);
        assertEquals(result,
                createStatistic(
                        createFullStringStatistic(1, 11, 11),
                        createFullIntegerStatistic(1, 123, 123, 123, 123),
                        createFullFloatStatistic(1, 123.0f, 123.0f, 123.0f, 123.0f)
                ));
    }

    private void createFile(String file, List<String> content) throws IOException {
        Path path = Files.createFile(Path.of(file));
        Files.write(path, content);
    }

    private void deleteFile(String file, String path) throws IOException {
        Files.delete(Path.of(file));
        Files.walk(Path.of(path))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }
}