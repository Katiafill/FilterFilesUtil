package ru.cft.shift.util.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ContentType {
    STRING("strings.txt"),
    INT("integers.txt"),
    FLOAT("floats.txt");

    private final String filename;
}
