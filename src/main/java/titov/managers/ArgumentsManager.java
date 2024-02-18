package titov.managers;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import titov.enums.Option;
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

    private boolean isExistsFile = false;
    private List<String> optionsList = new ArrayList<>();
    static final String FILE_EXTENSION = ".txt";
    static final String HYPHEN_CHAR = "-";

    public void processArguments() throws NotFoundException {
        log.debug("Обработка данных, полученных из командной строки - Старт.");
        existsArgs(arguments.length);
        for (String argument : arguments) {
            if (argument.endsWith(FILE_EXTENSION)) {
                processFile(argument);
            } else {
                optionsList.add(argument);
            }
        }
        processOptions();
        existsFile(isExistsFile);
        log.debug("Обработка данных, полученных из командной строки - Финиш.");
    }

    private void existsArgs(int numberArguments) throws NotFoundException {
        if (numberArguments == 0) {
            System.err.println("При запуске утилиты в командную строку не были переданы вводные данные и условия. " +
                    "Попробуйте ввести корректные данные и запустите программу ещё раз.");
            log.error("Ошибка: программа запущена без данных.");
            throw new NotFoundException("В командную строку не были введены данные для работы утилиты.");
        }
    }

    private void processFile(String argument) {
        log.debug("Через командную строку пользователем было передано имя файла.");
        filesList.add(argument);
        if (!isExistsFile) {
            isExistsFile = true;
        }
    }

    private void processOptions() {
        log.debug("Через командную строку пользователем была передана опция фильтрации данных.");
        int optionListSize = optionsList.size();
        for (int i = 0; i < optionListSize; i++) {
            String option = optionsList.get(i);
            if (option.equals(Option.FULL_STAT.toString())) {
                processFullStatOption();
            } else if (option.equals(Option.SHORT_STAT.toString())) {
                processShortStatOption();
            } else if (option.equals(Option.ADD_TO_FILE.toString())) {
                processAddToFileOption();
            } else if (option.equals(Option.PREFIX.toString())) {
                if (i == optionListSize - 1) {
                    System.out.println("Опции \"-p\" не передали префикс при запуске утилиты. Сохранение файла " +
                            "произойдёт по умолчанию.");
                    log.info("Опции \"-p\" не передали префикс при запуске утилиты.");
                    continue;
                }
                if (processPrefixFileOption(optionsList.get(i + 1))) {
                    i++;
                }
            } else if (option.equals(Option.PATH.toString())) {
                if (i == optionListSize - 1) {
                    System.out.println("Опции \"-o\" не передали путь к новой папке при запуске утилиты. Сохранение " +
                            "файла произойдёт по умолчанию.");
                    log.info("Опции \"-o\" не передали путь к папке при запуске утилиты.");
                    continue;
                }
                if (processPathFileOption(optionsList.get(i + 1))) {
                    i++;
                }
            } else {
                System.out.printf("Комбинация символов \"%s\" не соответствует ни одной команде.\n", option);
                log.info("Комбинация символов \"{}\" не соответствует ни одной команде.", option);
            }
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

    private boolean processPrefixFileOption(String nextArgument) {
        if (nextArgument.startsWith(HYPHEN_CHAR) || nextArgument.endsWith(FILE_EXTENSION)) {
            prefixResultFile = "";
            System.out.println("Опции \"-p\" не передали префикс при запуске утилиты. Сохранение файла " +
                    "произойдёт по умолчанию.");
            log.info("Опции \"-p\" не передали префикс при запуске утилиты.");
            return false;
        } else {
            log.debug("Активирована опция -p");
            prefixResultFile = nextArgument;
            return true;
        }
    }

    private boolean processPathFileOption(String nextArgument) {
        if (nextArgument.startsWith(HYPHEN_CHAR) || nextArgument.endsWith(FILE_EXTENSION)) {
            pathResultFile = "";
            System.out.println("Опции \"-o\" не передали путь к новой папке при запуске утилиты. Сохранение файла " +
                    "произойдёт по умолчанию.");
            log.info("Опции \"-o\" не передали путь к папке при запуске утилиты.");
            return false;
        } else {
            log.debug("Активирована опция -o");
            pathResultFile = nextArgument;
            return true;
        }
    }

    private void existsFile(boolean isExistsFile) throws NotFoundException {
        if (!isExistsFile) {
            System.err.println("При запуске утилиты в командную строку не был передан ни один файл для фильтрации. " +
                    "Выполнить операцию невозможно, попробуйте запустить программу ещё раз.");
            log.error("Ошибка: программа запущена без файлов.");
            throw new NotFoundException("Через командную строку не были введены имена файлов для фильтрации.");
        }
    }

}