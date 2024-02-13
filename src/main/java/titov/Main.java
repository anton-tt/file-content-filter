package titov;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import titov.exception.DataTypeNotFoundException;
import titov.exception.NotFoundException;
import titov.managers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class Main {


    public static void main(String[] args) throws IOException, NotFoundException, DataTypeNotFoundException {

        log.info("!!!!!!!!!!!!!!!!!!!Подготовка к чтению строк из файлов в количестве  штук.");

        ArgumentsManager argumentsManager = new ArgumentsManager(args);
        argumentsManager.processArguments();
        List<String> filesList = new ArrayList<>(argumentsManager.getFilesList());
        System.out.println(filesList);


        FileReaderManager fileReaderManager = new FileReaderManager(argumentsManager.getFilesList());
        List<String> filesTtList = new ArrayList<>(fileReaderManager.getFilesList());
        System.out.println(filesTtList);
        fileReaderManager.readFiles();
        List<String> stringList = new ArrayList<>(fileReaderManager.getStringsList());
        System.out.println(stringList);

        StringsManager stringsManager = new StringsManager(fileReaderManager.getStringsList());
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

    }
}

/* <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.4.14</version>
        </dependency>*/