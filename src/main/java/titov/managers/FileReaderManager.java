package titov.managers;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import titov.exception.NotFoundException;

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
            try {
                if (Files.exists(path)) {
                    readFileContents(path);
                    if (!isExistsFile) {
                        log.debug("Один файл точно найден и прочитан. Обработка данных будет продолжена.");
                        isExistsFile = true;
                    }
                } else {
                    throw new NotFoundException(String.format("Файл с данными \"%s\", переданный пользователем " +
                            "в командную строку, не найден.", fileData));
                }
            } catch (NotFoundException ex) {
                System.err.printf("Файл с данными \"%s\" не найден. Проверьте, правильно ли введены параметры " +
                        "при запуске утилиты, и повторите свой запрос после окончания работы программы.\n",
                        fileData);
                log.warn("Ошибка: " + ex);
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
        log.debug("Чтение содержимого файла будет по следующему пути: " + path);
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                stringsList.add(line);
            }
            readFileList.add(path.getFileName().toString());
        } catch (IOException ex) {
            System.err.printf("При чтении файла с данными \"%s\" произошла непредвиденная ошибка. Работа утилиты " +
                    "будет продолжена. По её окончании рекомендуется перезапустить программу и повторить обработку " +
                    "этого файла.\n", path);
            log.warn("Ошибка: " + ex);
        }
    }

    private void existsFiles(boolean isExistsFile) throws FileNotFoundException {
        log.debug("Итоговая проверка перед фильтрацией данных: обработан ли хотя бы один файл.");
        if (!isExistsFile) {
            System.err.println("По данным, переданным при запуске утилиты, не был найден ни один файл. Программа " +
                    "закончила работу. Попробуйте повторить свой запрос с другими вводными данными.");
            log.error("Ни одного файла с данными для обработки не было найдено.");
            throw new FileNotFoundException("Ни один из файлов, переданных пользователем в командную строку, " +
                    "не найден программой.");
        }
    }

}