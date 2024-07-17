package ru.cft.shift.util.parameters;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Optional;

import static ru.cft.shift.util.parameters.UtilParametersException.ExceptionType.*;


@Slf4j
class UtilParametersParser {
    private final String[] args;
    private int currentStep;
    private UtilParameters.UtilParametersBuilder builder;
    private ArrayList<String> files;

    private boolean statisticIsSet;

    UtilParametersParser(String[] args) throws UtilParametersException {
        if (args.length < 1) {
            throw new UtilParametersException(NO_FILES);
        }

        this.args = args;
    }

    UtilParameters parse() throws UtilParametersException {
        builder = UtilParameters.builder();
        files = new ArrayList<>();

        for (currentStep = 0; currentStep < args.length; currentStep++) {
            String arg = args[currentStep];

            if (!extractOption(arg)) {
                setInputFile(arg);
            }
        }

        if (files.isEmpty()) {
            throw new UtilParametersException(NO_FILES);
        }

        builder.files(files);

        return builder.build();
    }

    private void setInputFile(String arg) throws UtilParametersException {
        if (arg.endsWith(".txt")) {
            files.add(arg);
        } else {
            throw new UtilParametersException(INVALID_FILE_FORMAT);
        }
    }

    private boolean extractOption(String arg) throws UtilParametersException {
        Optional<OptionType> optionType = OptionType.fromString(arg);
        if (optionType.isEmpty()) {
            return false;
        }

        switch (optionType.get()) {
            case PATH -> setFilePath();
            case PREFIX -> setFilePrefix();
            case APPEND_FILE -> setAppendFileParameter();
            case SHORT_STATISTIC -> setStatisticMode(false);
            case FULL_STATISTIC -> setStatisticMode(true);
        }

        return true;
    }

    private void setFilePrefix() throws UtilParametersException {
        // Сделаем допущение, что если вдруг встретится второй такой флаг, то значение просто перезапишется.
        if (++currentStep < args.length) {
            builder.prefix(args[currentStep]);
        } else {
            throw new UtilParametersException(INVALID_PREFIX_OPTION);
        }
    }

    private void setFilePath() throws UtilParametersException {
        // Сделаем допущение, что если вдруг встретится второй такой флаг, то значение просто перезапишется.
        if (++currentStep < args.length) {
            builder.path(args[currentStep]);
        } else {
            throw new UtilParametersException(INVALID_PATH_OPTION);
        }
    }

    private void setAppendFileParameter() {
        // Сделаем допущение, что двойное вхожждение этого флага ни на что не повлияет.
        builder.isAppendFile(true);
    }

    private void setStatisticMode(boolean isFull) throws UtilParametersException {
        // Запрещаем двойное вхожждение и разные вхождения этого параметра.
        if (statisticIsSet) {
            throw new UtilParametersException(INVALID_STATISTIC_OPTION);
        }

        builder.isFullStatistic(isFull);
        statisticIsSet = true;
    }

}
