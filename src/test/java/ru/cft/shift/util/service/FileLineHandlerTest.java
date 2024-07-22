package ru.cft.shift.util.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class FileLineHandlerTest {

    private FileLineHandler handler;

    @BeforeEach
    void setUp() {
        handler = new FileLineHandler(false);
    }

    @Test
    void parseInt() {
        ContentType type = handler.parse("1");
        assertEquals(type, ContentType.INT);
    }

    @Test
    void parseInt_negative() {
        ContentType type = handler.parse("-10");
        assertEquals(type, ContentType.INT);
    }

    @Test
    void parseInt_whitespace() {
        ContentType type = handler.parse(" -10 ");
        assertEquals(type, ContentType.INT);
    }

    @Test
    void parseFloat() {
        ContentType type = handler.parse("1.0");
        assertEquals(type, ContentType.FLOAT);
    }

    @Test
    void parseFloat_negative() {
        ContentType type = handler.parse("-16.89");
        assertEquals(type, ContentType.FLOAT);
    }

    @Test
    void parseFloat_whitespace() {
        ContentType type = handler.parse(" 234.5 ");
        assertEquals(type, ContentType.FLOAT);
    }

    @Test
    void parseFloat_typeCharacter() {
        ContentType type = handler.parse(" 234.5f ");
        assertEquals(type, ContentType.FLOAT);
    }

    @Test
    void parseString() {
        ContentType type = handler.parse("sample string");
        assertEquals(type, ContentType.STRING);
    }

    @Test
    void parseString_withInteger() {
        ContentType type = handler.parse("sample string with 12345");
        assertEquals(type, ContentType.STRING);
    }

    @Test
    void parseString_withFloat() {
        ContentType type = handler.parse("sample string with 12.35");
        assertEquals(type, ContentType.STRING);
    }

    @Test
    void checkStatistic() {
        // Проверим, что статистика собирается.
        handler.parse("1");
        handler.parse("1.0");
        handler.parse("sample");

        log.info(handler.getManager().getStatistic());
    }

}