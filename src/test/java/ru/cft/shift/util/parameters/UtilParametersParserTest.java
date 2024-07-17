package ru.cft.shift.util.parameters;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.cft.shift.util.parameters.UtilParametersException.ExceptionType.*;

class UtilParametersParserTest {

    @Test
    void emptyArgs() {
        UtilParametersException ex = assertThrows(UtilParametersException.class, () -> {
            UtilParameters.parse(new String[]{});
        });

        assertEquals(ex.getExceptionType(), NO_FILES);
    }
    
    @Test
    void invalidPath() {
        UtilParametersException ex = assertThrows(UtilParametersException.class, () -> {
            UtilParameters.parse(new String[]{"-o"});
        });

        assertEquals(ex.getExceptionType(), INVALID_PATH_OPTION);
    }
    
    @Test
    void invalidPrefix() {
        UtilParametersException ex = assertThrows(UtilParametersException.class, () -> {
            UtilParameters.parse(new String[]{"-p"});
        });

        assertEquals(ex.getExceptionType(), INVALID_PREFIX_OPTION);
    }
    
    @Test
    void invalidFormat() {
        invalidFormat(new String[] {"sample"});
        invalidFormat(new String[] {"sample.csv"});
        invalidFormat(new String[] {"sample.tx"});
    }

    private void invalidFormat(String[] args) {
        UtilParametersException ex = assertThrows(UtilParametersException.class, () -> {
            UtilParameters.parse(args);
        });

        assertEquals(ex.getExceptionType(), INVALID_FILE_FORMAT);
    }

    @Test
    void invalidStatisticOption() {
        UtilParametersException ex = assertThrows(UtilParametersException.class, () -> {
            UtilParameters.parse(new String[]{"-s", "-f"});
        });

        assertEquals(ex.getExceptionType(), INVALID_STATISTIC_OPTION);
    }

    @Test
    void onlyFiles() throws UtilParametersException {
        UtilParameters parameters = UtilParameters.builder()
                .files(List.of("sample.txt"))
                .build();

        UtilParameters expected = UtilParameters.parse(new String[] {
                "sample.txt"
        });
        assertEquals(expected, parameters);
    }

    @Test
    void pathAndPrefixOptions() throws UtilParametersException {
        UtilParameters parameters = UtilParameters.builder()
                .files(List.of("sample.txt"))
                .path("/output")
                .prefix("out_")
                .build();

        UtilParameters expected = UtilParameters.parse(new String[] {
                "-o", "/output",
                "-p", "out_",
                "sample.txt"
        });
        assertEquals(expected, parameters);
    }

    @Test
    void allOptions() throws UtilParametersException {
        UtilParameters parameters = UtilParameters.builder()
                .files(List.of("sample.txt"))
                .path("/output")
                .prefix("out_")
                .isAppendFile(true)
                .isFullStatistic(true)
                .build();

        UtilParameters expected = UtilParameters.parse(new String[] {
                "-o", "/output",
                "-p", "out_",
                "sample.txt",
                "-a",
                "-f"
        });
        assertEquals(expected, parameters);
    }

    @Test
    void allOptionsAndManyFiles() throws UtilParametersException {
        UtilParameters parameters = UtilParameters.builder()
                .files(List.of("in1.txt", "in2.txt"))
                .prefix("sample-")
                .isAppendFile(true)
                .isFullStatistic(false)
                .build();

        UtilParameters expected = UtilParameters.parse(new String[] {
                "-s",
                "-a",
                "-p", "sample-",
                "in1.txt",
                "in2.txt"
        });
        assertEquals(expected, parameters);
    }
}