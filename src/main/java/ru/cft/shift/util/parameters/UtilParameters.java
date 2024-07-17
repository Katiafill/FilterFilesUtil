package ru.cft.shift.util.parameters;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
public class UtilParameters {
    private final String path;
    private final String prefix;
    private final boolean isAppendFile;
    private final boolean isFullStatistic;
    private final List<String> files;

    public static UtilParameters parse(String[] args) throws UtilParametersException {
        return new UtilParametersParser(args).parse();
    }
}
