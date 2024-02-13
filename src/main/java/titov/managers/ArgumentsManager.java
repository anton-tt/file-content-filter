package titov.managers;

import lombok.Data;
import lombok.NonNull;
import titov.enums.Options;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
//@Slf4j
public class ArgumentsManager {

    private String prefixResultFile = "";
    private String pathResultFile = "";
    private boolean isAddToFile = false;
    private boolean isGetShortStat = false;
    private boolean isGetFullStat = false;
    private List<String> filesList = new ArrayList<>();
    @NonNull
    private final String[] arguments;

    String FILE_EXTENSION = ".txt";
    boolean isExistsFile = false;

    public void processArguments() {
        isExistsArgs(arguments.length);
        System.out.println(Arrays.toString(arguments));

        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].endsWith(FILE_EXTENSION)) {
                System.out.println("Файл был передан при запуске программы.");
                filesList.add(arguments[i]);
                if (!isExistsFile) {
                    isExistsFile = true;
                }
            } else {
                if (arguments[i].equals(Options.FULL_STAT.toString())) {
                    processFullStatOptions();
                } else if (arguments[i].equals(Options.SHORT_STAT.toString())) {
                    processShortStatOptions();
                } else if (arguments[i].equals(Options.ADD_TO_FILE.toString())) {
                    processAddToFileOptions();
                } else if (arguments[i].equals(Options.PREFIX.toString())) {

                    System.out.println(arguments[i]);
                    System.out.println(arguments[i + 1]);
                    processPrefixFileOptions(arguments[i + 1]);
                    i++;
                } else if (arguments[i].equals(Options.PATH.toString())) {
                    System.out.println(arguments[i]);
                    System.out.println(arguments[i + 1]);
                    processPathFileOptions(arguments[i + 1]);
                    i++;
                } else {
                    System.err.printf("Переданная в командную строку комбинация символов \"%s\" " +
                            "не соответствует ни имеющимся в программе опциям, ни названию файла. " +
                            "Попробуйте ввести корректные данные и перезапустите программу.%n", arguments[i]);
                }
            }
        }
        isExistsFile(isExistsFile);

    }


    private void isExistsArgs(int numberArguments) {
        if (numberArguments == 0) {
            System.out.println("При запуске утилиты в командную строку не были переданы вводные данные и условия. " +
                    "Выполнить операцию невозможно, попробуйте запустить программу ещё раз.");
        }
    }

    private void processShortStatOptions() {
        System.out.println("Опция -s");
        isGetShortStat = true;
    }

    private void processFullStatOptions() {
        System.out.println("Опция -f");
        isGetFullStat = true;
    }

    private void processAddToFileOptions() {
        System.out.println("Опция -a");
        isAddToFile = true;
    }

    private void processPrefixFileOptions(String nextArgument) {
        System.out.println("Опция -p");
        prefixResultFile = nextArgument; // добавить проверку на символы в префиксе
    }

    private void processPathFileOptions(String nextArgument) {
        System.out.println("Опция -o");
        pathResultFile = nextArgument;  // добавить проверку на символы в пути
    }

    private void isExistsFile(boolean isExistsFile) {
        if (!isExistsFile) {
            System.out.println("При запуске утилиты в командную строку не был передан ни один файл для фильтрации. " +
                    "Выполнить операцию невозможно, попробуйте запустить программу ещё раз.");
        }
    }

}