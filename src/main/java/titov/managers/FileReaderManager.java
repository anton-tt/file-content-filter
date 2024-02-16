package titov.managers;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class FileReaderManager {
    private final List<String> stringsList = new ArrayList<>();
    private final List<String> readFileList = new ArrayList<>();
    @NonNull
    private final List<String> filesList;

    static final String SEPARATOR = File.separator;

    public void readFiles() throws IOException {
        log.debug("Обработка файлов пользователя - Старт.");
        boolean isExistsFile = false;
        for (String fileData : filesList) {
            Path path = getFilePath(fileData);
            if (Files.exists(path)) {
                readFileContents(path);
                if (!isExistsFile) {
                    log.debug("Один файл точно найден и прочитан. Обработка данных будет продолжена. ");
                    isExistsFile = true;
                }
            } else {
                System.err.printf("Файл с данными \"%s\" не найден. Проверьте, правильно ли введены параметры " +
                        "при запуске программы, и повторите свой запрос после окончания работы программы.\n", fileData);
                log.warn("Пользовательский файл с данными \"{}\" не найден.", fileData);
            }
        }
        existsFiles(isExistsFile);
        log.debug("Обработка файлов пользователя - Финиш.");
    }

    public Path getFilePath(String fileData) {
        log.debug("Определение пути к файлу пользователя с данными \"{}\".", fileData);
        String PARENTS_DIRECTORY = "src" + SEPARATOR + "main" + SEPARATOR + "resources" + SEPARATOR;
        Path path;
        if (!fileData.contains(SEPARATOR)) {
            path = FileSystems.getDefault().getPath(PARENTS_DIRECTORY, fileData);
        } else {
            path = Paths.get(fileData);
        }
        return path;
    }

    private void readFileContents(Path path) {
        log.debug("Чтение содержимого файла по следующему пути: " + path);
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                stringsList.add(line);
            }
            readFileList.add(path.getFileName().toString());
        } catch (IOException ex) {
            System.out.printf("При чтении файла с данными \"%s\" произошла непредвиденная ошибка. " +
                    "Рекомендуется перезапустить программу и повторить обработку файла.\n", path);
            log.warn("Ошибка при чтении пользовательского файла: " + ex);
        }
    }

    private void existsFiles(boolean isExistsFile) throws FileNotFoundException {
        if (!isExistsFile) {
            throw new FileNotFoundException("По данным, переданным при запуске утилиты, не был найден ни один файл. " +
                    "Программа закончила работу. Попробуйте повторить свой запрос с другими вводными данными.");
        }
    }

}