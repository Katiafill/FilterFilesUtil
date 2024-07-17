package ru.cft.shift.util.parameters;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
enum OptionType {
    PATH("-o"),
    PREFIX("-p"),
    APPEND_FILE("-a"),
    SHORT_STATISTIC("-s"),
    FULL_STATISTIC("-f");

    private final String name;

    static Optional<OptionType> fromString(String name) {
        for (OptionType type: OptionType.values()) {
            if (type.getName().equals(name)) {
                return Optional.of(type);
            }
        }

        return Optional.empty();
    }
}
