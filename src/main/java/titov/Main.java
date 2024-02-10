package titov;

import titov.exception.NotFoundException;
import titov.managers.ArgumentsManager;
import titov.managers.FileReaderManager;
import titov.managers.StringsManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//@Slf4j
public class Main {



    public static void main(String[] args) throws IOException, NotFoundException {

        ArgumentsManager argumentsManager = new ArgumentsManager(args);
        argumentsManager.processArguments();
        String prefixResultFile = argumentsManager.getPrefixResultFile();
        System.out.println(prefixResultFile);
        String pathResultFile = argumentsManager.getPathResultFile();
        System.out.println(pathResultFile);
        boolean isGetShortStat = argumentsManager.isGetShortStat();
        System.out.println(isGetShortStat);
        boolean isGetFullStat = argumentsManager.isGetFullStat();
        System.out.println(isGetFullStat);
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
    }
}

/* <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.4.14</version>
        </dependency>*/