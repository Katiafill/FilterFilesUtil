package ru.cft.shift.util.parameters;

import lombok.Getter;

@Getter
public class UtilParametersException extends Exception {
    public enum ExceptionType {
        NO_FILES, //"Invalid command parameters. Should be filename."
        INVALID_FILE_FORMAT, //"Invalid command parameters. File should be txt format."
        INVALID_PREFIX_OPTION, //"Invalid command parameters. After option -p should be prefix value."
        INVALID_PATH_OPTION, //"Invalid command parameters. After option -o should be path value."
        INVALID_STATISTIC_OPTION //"Invalid command parameters. Option -s or -f should be once."
    }

    private final ExceptionType exceptionType;

    public UtilParametersException(ExceptionType exceptionType) {
        super();
        this.exceptionType = exceptionType;
    }
}
