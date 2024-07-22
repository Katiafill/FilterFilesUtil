package ru.cft.shift.util.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.cft.shift.util.parameters.UtilParameters;

@Slf4j
@RequiredArgsConstructor
public class UtilService {
    private final UtilParameters parameters;

    public String run() {
        FileLineHandler lineHandler = new FileLineHandler(parameters.isFullStatistic());

        FileWriterServiceParameters fileWriterServiceParameters = getFileWriterServiceParameters();
        try(FileReaderService readerService =
                    new FileReaderServiceImpl(parameters.getFiles());
            FileWriterService writerService =
                    new FileWriterServiceImpl(fileWriterServiceParameters) ) {


            readerService.readFiles(line -> {
                ContentType type = lineHandler.parse(line);
                writerService.writeLine(line, type);
            });
        } catch (Exception e) {
            log.error("Exception occurred while filtering files.", e);
        }

        return lineHandler.getManager().getStatistic();
    }

    private FileWriterServiceParameters getFileWriterServiceParameters() {
       return new FileWriterServiceParameters(
               parameters.getPath(),
               parameters.getPrefix(),
               parameters.isAppendFile()
       );
    }
}
