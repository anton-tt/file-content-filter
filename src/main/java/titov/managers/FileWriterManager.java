package titov.managers;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import titov.enums.DataType;
import titov.exception.DataTypeNotFoundException;
import titov.exception.PathNotFoundException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Data
@Slf4j
public class FileWriterManager {
    @NonNull
    private String prefixResultFile;
    @NonNull
    private String pathResultFile;
    private final boolean isAddToFile;
    @NonNull
    private final List<Long> integersList;
    @NonNull
    private final List<Float> floatsList;
    @NonNull
    private final List<String> stringsList;

    static final String TRANSFER = System.lineSeparator();
    static final String SEPARATOR = File.separator;
    static final String DEFAULT_PREFIX = "default-";

    public void writerFiles() throws DataTypeNotFoundException {
        log.debug("Сохранение данных в файлы - Старт.");
        writerIntegers(getFilePath(DataType.LONG));
        writerFloats(getFilePath(DataType.FLOAT));
        writerStrings(getFilePath(DataType.STRING));
        log.debug("Сохранение данных в файлы - Финиш.");
    }

    private void writerIntegers(String pathString) {
        int integersNumber = integersList.size();
        log.debug("Запись целых чисел по пути \"{}\" в количестве {} элементов.", pathString, integersNumber);

        if (integersNumber > 0) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(
                    new FileWriter(pathString, StandardCharsets.UTF_8, isAddToFile))) {
                for (long item : integersList) {
                    bufferedWriter.write(Long.toString(item));
                    bufferedWriter.write(TRANSFER);
                }
                log.info("В файл {} сохранено целых чисел - {}.", pathString, integersNumber);
            } catch (IOException ex) {
                System.out.printf("При сохранении в файл \"%s\" целых чисел произошла непредвиденная ошибка. " +
                        "Рекомендуется повторно запустить программу и перезаписать файлы.\n", pathString);
                log.warn("Ошибка при сохранении в файл целых чисел: " + ex);
            }
        } else {
            log.debug("При обработке вводных данных не было найдено ни одного целого числа." +
                    "В файл \"{}\" никакой информации не добавлялось.", pathString);
        }
    }

    private void writerFloats(String pathString) {
        int floatsNumber = floatsList.size();
        log.debug("Запись дробных чисел по пути \"{}\" в количестве {} элементов.", pathString, floatsNumber);

        if (floatsNumber > 0) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(
                    new FileWriter(pathString, StandardCharsets.UTF_8, isAddToFile))) {
                for (float item : floatsList) {
                    bufferedWriter.write(Float.toString(item));
                    bufferedWriter.write(TRANSFER);
                }
                log.info("В файл {} сохранено дробных чисел - {}.", pathString, floatsNumber);
            } catch (IOException ex) {
                System.out.printf("При сохранении в файл \"%s\" дробных чисел произошла непредвиденная ошибка. " +
                        "Рекомендуется повторно запустить программу и перезаписать файлы.\n", pathString);
                log.warn("Ошибка при сохранении в файл дробных чисел: " + ex);
            }
        } else {
            log.debug("При обработке вводных данных не было найдено ни одного дробного числа." +
                    "В файл \"{}\" никакой информации не добавлялось.", pathString);
        }
    }

    private void writerStrings(String pathString) {
        int stringsNumber = stringsList.size();
        log.debug("Запись строк по пути \"{}\" в количестве {} элементов.", pathString, stringsNumber);

        if (stringsNumber > 0) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(
                    new FileWriter(pathString, StandardCharsets.UTF_8, isAddToFile))) {
                for (String item : stringsList) {
                    bufferedWriter.write(item);
                    bufferedWriter.write(TRANSFER);
                }
                log.info("В файл {} сохранено строк - {}.", pathString, stringsNumber);
            } catch (IOException ex) {
                System.out.printf("При сохранении в файл \"%s\" строк произошла непредвиденная ошибка. " +
                        "Рекомендуется повторно запустить программу и перезаписать файлы.\n", pathString);
                log.warn("Ошибка при сохранении в файл строк: " + ex);
            }
        } else {
            log.debug("При обработке вводных данных не было найдено ни одной строки." +
                    "В файл \"{}\" никакой информации не добавлялось.", pathString);
        }
    }

    private String getFilePath(DataType dataType) throws DataTypeNotFoundException {
        return getFileDirectory() + getFileName(dataType);
    }

    private String getFileName(DataType dataType) throws DataTypeNotFoundException {
        log.debug("Определение имён файлов для сохранения данных.");
        String fileName;
        isValidPrefixFile();

        switch (dataType){
            case LONG:
                fileName = prefixResultFile + "integers.txt";
                break;
            case FLOAT:
                fileName = prefixResultFile + "floats.txt";
                break;
            case STRING:
                fileName = prefixResultFile + "strings.txt";
                break;
            default:
                log.error("Программа не поддерживает тип данных \"{}\". Необходимо проверить, " +
                        "как данный тип появился в коде.", dataType);
                throw new DataTypeNotFoundException("Тип данных для записи в файл не поддерживается программой.");
        }
        return fileName;
    }

    private String getFileDirectory() {
        log.debug("Определение пути, по которому будут сохраняться файлы.");
        String PARENTS_DIRECTORY = "src" + SEPARATOR + "main" + SEPARATOR + "resources" + SEPARATOR;

        try {
            isExistDirectory();
        } catch (PathNotFoundException ex) {
            System.err.printf("По пути \"%s\", введённому при запуске программы, не найдена необходимая папка. " +
                    "Файлы будут сохранены в папке по умолчанию с префиксом \"%s\".\n", pathResultFile,
                    DEFAULT_PREFIX);
            log.warn("Папка для сохранения файлов не обнаружена. Сохранение по умолчанию. ", ex);
            pathResultFile = "";
            prefixResultFile = prefixResultFile + DEFAULT_PREFIX;
        }

        if (pathResultFile.isBlank() ) {
            return PARENTS_DIRECTORY;
        } else {
            return pathResultFile + File.separator;
        }
    }

    private void isExistDirectory() throws PathNotFoundException {
        if (!Files.isDirectory(Path.of(pathResultFile))) {
            throw new PathNotFoundException("Папка, путь к которой ввёл пользователь при запуске программы, " +
                    "не найдена.");
        }
    }

    private void isValidPrefixFile() {
        String regex = ""; //подобрать выражение
        if (prefixResultFile.matches(regex)) {
            log.info("Введённый пользователем префикс {} содержит запрещённые для имени файла символы.", prefixResultFile);
            System.err.printf("Введённый префикс для имени файла содержит запрещённые символы. " +
                    "Файлы будут сохранены с префиксом \"%s\". \n", DEFAULT_PREFIX);
            prefixResultFile = DEFAULT_PREFIX;
        }
    }

}