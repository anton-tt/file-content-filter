package titov.managers;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Data
//@Slf4j
public class FileReaderManager {
    private List<String> stringsList = new ArrayList<>();
    private List<String> readFileList = new ArrayList<>();
    @NonNull
    private final List<String> filesList;

    String PARENTS_DIRECTORY = "src/main/resources/";
    boolean isExistsFile = false;

    // чтобы исключение не останавливало работу программы
    public void readFiles() throws IOException {
        //log.info("Подготовка к чтению строк из файлов в количестве {} штук.", filesList.size());
        for (String fileData : filesList) {
            Path path = getFilePath(fileData);
            if (Files.exists(path)) {
                readFileContents(path);
                if (!isExistsFile) {
                    //log.info("Ставим метку, что хотя бы однин файл найден и прочитан.");
                    isExistsFile = true;
                }
                //log.info("Файл обработан успешно.");
            } else {  //?????
                System.err.printf("Файл с данными \"%s\" не найден. Проверьте, правильно ли введены параметры " +
                        "при запуске программы, и повторите свой запрос после окончания работы программы.", fileData);
            }
        }

        isExistsFiles(isExistsFile);
        //log.info("Успешно выполнено чтение {} файлов.", readFileList.size());
    }

    public Path getFilePath(String fileData) {
        //log.info("Обрабатываются данные по файлу, полученные через командную строку: " + fileData);
        Path path;
        if (!fileData.contains("\\")) { // прямой слеш??
            path = FileSystems.getDefault().getPath(PARENTS_DIRECTORY, fileData);
        } else {
            path = Paths.get(fileData);
        }
        return path;
    }

    private void readFileContents(Path path) throws IOException {
        //log.info("Начинается чтение содержимого файла по следующему пути: " + path);
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                stringsList.add(line);
            }
            readFileList.add(path.getFileName().toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();  // своё исключение?
        }
    }

    private void isExistsFiles(boolean isExistsFile) throws FileNotFoundException {
        if (!isExistsFile) {
            throw new FileNotFoundException("По данным, переданным при запуске утилиты, не был найден ни один файл. " +
                    "Программа закончила работу. Попробуйте повторить свой запрос с другими вводными данными.");
        }
    }

}