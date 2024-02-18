package titov;

import lombok.extern.slf4j.Slf4j;

import titov.exception.DataTypeNotFoundException;
import titov.exception.NotFoundException;
import titov.managers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class Main {

    public static void main(String[] args) throws IOException, NotFoundException,
            DataTypeNotFoundException {
        log.debug("Запуск программы.");
        ArgumentsManager argumentsManager = new ArgumentsManager(args);
        argumentsManager.processArguments();
        List<String> filesList = new ArrayList<>(argumentsManager.getFilesList());

        FileReaderManager fileReaderManager = new FileReaderManager(filesList);
        fileReaderManager.readFiles();
        List<String> stringList = new ArrayList<>(fileReaderManager.getStringsList());

        StringsManager stringsManager = new StringsManager(stringList);
        stringsManager.filterStrings();
        List<Long> integersList = new ArrayList<>(stringsManager.getIntegersList());
        List<Float> floatsList = new ArrayList<>(stringsManager.getFloatsList());
        List<String> stringsList = new ArrayList<>(stringsManager.getStringsList());

        boolean isShortStat = argumentsManager.isGetShortStat();
        boolean isFullStat = argumentsManager.isGetFullStat();
        StatManager statManager = new StatManager(isShortStat, isFullStat,
                integersList, floatsList, stringsList);
        statManager.getStat();

        String prefixResultFile = argumentsManager.getPrefixResultFile();
        String pathResultFile = argumentsManager.getPathResultFile();
        boolean isAddToFile = argumentsManager.isAddToFile();
        FileWriterManager fileWriterManager = new FileWriterManager(prefixResultFile, pathResultFile, isAddToFile,
                integersList, floatsList, stringsList);
        fileWriterManager.writerFiles();
        log.debug("Работа программы завершилась в плановом порядке.");
    }

}