package titov.managers;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import titov.enums.Option;
import titov.exception.BadRequestException;
import titov.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class ArgumentsManager {

    private String prefixResultFile = "";
    private String pathResultFile = "";
    private boolean isAddToFile = false;
    private boolean isGetShortStat = false;
    private boolean isGetFullStat = false;
    private List<String> filesList = new ArrayList<>();
    @NonNull
    private final String[] arguments;

    boolean isExistsFile = false;


    public void processArguments() throws NotFoundException {
        log.debug("Обработка данных, полученных из командной строки - Старт.");
        existsArgs(arguments.length);
        String FILE_EXTENSION = ".txt";

        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].endsWith(FILE_EXTENSION)) {
                processFile(arguments[i]);
            } else {
                try {
                    if (arguments[i].equals(Option.FULL_STAT.toString())) {
                        processFullStatOption();
                    } else if (arguments[i].equals(Option.SHORT_STAT.toString())) {
                        processShortStatOption();
                    } else if (arguments[i].equals(Option.ADD_TO_FILE.toString())) {
                        processAddToFileOption();
                    } else if (arguments[i].equals(Option.PREFIX.toString())) {
                        processPrefixFileOption(arguments[i + 1]);
                        i++;
                    } else if (arguments[i].equals(Option.PATH.toString())) {
                        processPathFileOption(arguments[i + 1]);
                        i++;
                    } else {
                        throw new BadRequestException("Комбинация символов не соответствует ни одной команде.");
                    }
                } catch (BadRequestException ex) {
                    System.err.printf("Переданная в командную строку комбинация символов \"%s\" " +
                            "не соответствует ни имеющимся в программе опциям, ни названию файла. " +
                            "Попробуйте ввести корректные данные и перезапустите программу.\n", arguments[i]);
                    log.warn("Ошибка при введении данных в командную строку: " + ex);
                }
            }
        }
        existsFile(isExistsFile);
        log.debug("Обработка данных, полученных из командной строки - Финиш.");
    }


    private void existsArgs(int numberArguments) throws NotFoundException {
        if (numberArguments == 0) {
            System.err.println("При запуске утилиты в командную строку не были переданы вводные данные и условия. " +
                    "Попробуйте ввести корректные данные и запустите программу ещё раз.");
            throw new NotFoundException("В командную строку не были введены данные для работы утилиты.");
        }
    }

    private void processFile(String argument) {
        filesList.add(argument);
        if (!isExistsFile) {
            log.debug("Один файл точно передан пользователем в командную строку. Обработка данных будет продолжена.");
            isExistsFile = true;
        }
    }


    private void processShortStatOption() {
        log.debug("Активирована опция -s");
        isGetShortStat = true;
    }

    private void processFullStatOption() {
        log.debug("Активирована опция -f");
        isGetFullStat = true;
    }

    private void processAddToFileOption() {
        log.debug("Активирована опция -a");
        isAddToFile = true;
    }

    private void processPrefixFileOption(String nextArgument) {
        log.debug("Активирована опция -p");
        prefixResultFile = nextArgument;
    }

    private void processPathFileOption(String nextArgument) {
        log.debug("Активирована опция -o");
        pathResultFile = nextArgument;
    }

    private void existsFile(boolean isExistsFile) throws NotFoundException {
        if (!isExistsFile) {
            log.error("Ошибка!");
            System.err.println("При запуске утилиты в командную строку не был передан ни один файл для фильтрации. " +
                    "Выполнить операцию невозможно, попробуйте запустить программу ещё раз.");
            throw new NotFoundException("В командную строку не были введены данные для работы утилиты.");
        }
    }

}