package titov.managers;

import lombok.Data;
import lombok.NonNull;
import titov.enums.DataType;
import titov.exception.NotFoundException;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Data
public class FileWriterManager {
    @NonNull
    private String prefixResultFile;
    @NonNull
    private String pathResultFile;
    @NonNull
    private boolean isAddToFile;
    @NonNull
    private List<Long> integersList;
    @NonNull
    private List<Float> floatsList;
    @NonNull
    private List<String> stringsList;

    public void writerFiles() {
        writerIntegers();
        writerFloats();
        writerStrings();
    }

    private void writerIntegers(Path path) {
        if (integersList.size() > 0) {
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
                String transfer = System.lineSeparator();
                for (long item : integersList) {
                    bufferedWriter.write(Long.toString(item));
                    bufferedWriter.write(transfer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("При обработке вводных данных не найдено ни одного целого числа." +
                    "В соответсвующий файл никакой информации не добавлялось.");
        }
    }

    private void writerFloats(Path path) {
        if (floatsList.size() > 0) {
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
                String transfer = System.lineSeparator();
                for (float item : floatsList) {
                    bufferedWriter.write(Float.toString(item));
                    bufferedWriter.write(transfer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("При обработке вводных данных не найдено ни одного дробного числа." +
                    "В соответсвующий файл никакой информации не добавлялось.");
        }
    }

    private void writerStrings(Path path) {
        if (stringsList.size() > 0) {
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
                String transfer = System.lineSeparator();
                for (String item : stringsList) {
                    bufferedWriter.write(item);
                    bufferedWriter.write(transfer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("При обработке вводных данных не найдено ни одной строки." +
                    "В соответсвующий файл никакой информации не добавлялось.");
        }
    }

    private Path getFilePath(String fileData) {
        //log.info("Обрабатываются данные по файлу, полученные через командную строку: " + fileData);
        Path path;
        /*if (pathResultFile.isBlank()) {
            path = FileSystems.getDefault().getPath(PARENTS_DIRECTORY, fileData);
        } else {
            path = Paths.get(fileData);
        }
        return path;*/
    }

    private String getFileName(DataType dataType) throws NotFoundException {
        String fileName;
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
                throw new NotFoundException("Тип данных не поддерживается программой."); // ИСКЛЮЧЕНИЕ
        }
        return fileName;
    }


}
