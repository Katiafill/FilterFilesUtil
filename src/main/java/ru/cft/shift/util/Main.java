package ru.cft.shift.util;

import lombok.extern.slf4j.Slf4j;
import ru.cft.shift.util.info.InfoService;
import ru.cft.shift.util.parameters.UtilParameters;
import ru.cft.shift.util.parameters.UtilParametersException;
import ru.cft.shift.util.service.UtilService;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try {
            UtilParameters parameters = UtilParameters.parse(args);
            UtilService service = new UtilService(parameters);
            String statistic = service.run();
            InfoService.getInstance().result(statistic);
        } catch (UtilParametersException ex) {
            InfoService.getInstance().error(ex.getMessage());
        }
    }
}