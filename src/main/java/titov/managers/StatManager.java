package titov.managers;

import lombok.Data;
import lombok.NonNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
//@Slf4j
public class StatManager {
    @NonNull
    private boolean isGetShortStat;
    @NonNull
    private boolean isGetFullStat;
    @NonNull
    private List<Long> integersList;
    @NonNull
    private List<Float> floatsList;
    @NonNull
    private List<String> stringsList;

    public void getStat() {
       if (isGetShortStat) {
           System.out.println("Краткая статистика по обработке полученных данных:");
           getShortStat();
       }
       if (isGetFullStat) {
           System.out.println("Полная статистика по обработке полученных данных.");
           getIntegersStat();
           getFloatsStat();
           getStringsStat();
       }
       if (!isGetShortStat && !isGetFullStat) {
           System.out.println("Опции с выводом статистики не были выбраны пользователем.");
       }
    }

    private void getShortStat() {
        System.out.printf("В категории \"Целые числа\" отсортировано и внесено в файл %s элементов.", integersList.size());
        System.out.printf("В категории \"Дробные числа\" отсортировано и внесено в файл %s элементов.", floatsList.size());
        System.out.printf("В категории \"Строки\" отсортировано и внесено в файл %s элементов.", stringsList.size());
    }

    private void getIntegersStat() {
        System.out.println(integersList);
        int integersNumber = integersList.size();
        Long sum = integersList.stream().reduce((long) 0, Long::sum);
        long average = sum/integersNumber;
        System.out.println("В категории \"Целые числа\":");
        System.out.printf("* отсортировано элементов и внесено в файл: %s,\n", integersNumber);
        System.out.printf("* минимальное число: %s,\n", integersList.get(0));
        System.out.printf("* максимальное число:  %s,\n", integersList.get(integersNumber - 1));
        System.out.printf("* сумма всех отсортированных чисел: %s,\n", sum);
        System.out.printf("* среднее арифметическое всех отсортированных чисел:  %s.\n\n", average);
    }

    private void getFloatsStat() {
        int floatsNumber = floatsList.size();
        float sum = floatsList.stream().reduce((float) 0, Float::sum);
        float average = sum/floatsNumber;
        System.out.println("В категории \"Дробные числа\":");
        System.out.printf("* отсортировано элементов и внесено в файл: %s,\n", floatsNumber);
        System.out.printf("* минимальное число: %s,\n", floatsList.get(0));
        System.out.printf("* максимальное число:  %s,\n", floatsList.get(floatsNumber - 1));
        System.out.printf("* сумма всех отсортированных чисел: %s,\n", sum);
        System.out.printf("* среднее арифметическое всех отсортированных чисел:  %s.\n\n", average);
    }

    private void getStringsStat() {
        List<Integer> stringLengthsList = stringsList.stream()
                .map(String::length)
                .sorted().collect(Collectors.toList());
        int stringLengthsNumber = stringLengthsList.size();
        System.out.println("В категории \"Строки\":");
        System.out.printf("* отсортировано элементов и внесено в файл: %s,\n", stringLengthsNumber);
        System.out.printf("* размер минимальноЙ строки: %s,\n", stringLengthsList.get(0));
        System.out.printf("* размер максимальной строки:  %s.\n", stringLengthsList.get(stringLengthsNumber - 1));
    }

}