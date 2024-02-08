package titov;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ArgumentsManager {

    boolean isExistsFile = false;
    String prefixResultFile = "";
    String pathResultFile = "";
    boolean isGetShortStat = false;
    boolean isGetFullStat = false;
    @NonNull
    private String[] arguments;
    List<String> filesList;

    ArgumentsManager(String[] args) {
        this.arguments = args;
        this.filesList = new ArrayList<>();
    }

    //int numberArguments = arguments.length;
    String FILE_EXTENSION = ".txt";

    protected void processArguments() {
        isExistsArgs(arguments.length);

        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].endsWith(FILE_EXTENSION)) {
                System.out.println("Файл был передан при запуске программы.");
                filesList.add(arguments[i]);
                if (!isExistsFile) {
                    isExistsFile = true;
                }
            } else {
                if (arguments[i].equals(Options.FULL_STAT.toString())) {
                    processFullStatOptions(arguments[i]);

                } else if (arguments[i].equals(Options.SHORT_STAT.toString())) {
                    processShortStatOptions(arguments[i]);
                } else if (arguments[i].equals(Options.PREFIX.toString())) {
                    processPrefixFileOptions(arguments[i + 1]);
                    i++;
                } else if (arguments[i].equals(Options.PATH.toString())) {
                    processPathFileOptions(arguments[i + 1]);
                    i++;
                } else {
                    System.out.printf("Переданная в командную строку комбинация символов \"%s\" " +
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

    private void processShortStatOptions(String argument) {
        System.out.println("Опция -s");
        isGetShortStat = true;
    }

    private void processFullStatOptions(String argument) {
        System.out.println("Опция -f");
        isGetFullStat = !isGetFullStat;
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