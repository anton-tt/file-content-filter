package titov.managers;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import titov.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Slf4j
public class StringsManager {
    private final List<Long> integersList = new ArrayList<>();
    private final List<Float> floatsList = new ArrayList<>();
    private final List<String> stringsList = new ArrayList<>();
    @NonNull
    private final List<String> dataList;

    public void filterStrings() throws NotFoundException {
        log.debug("Фильтрация данных по типам - Старт.");
        String intRegexp = "^[+-]?\\d+$";
        String floatRegexp = "^[+-]?\\d+\\.\\d+((e|E)(\\-|\\+)?\\d+)?$";

        isExistsStrings();
        log.debug("Разделение данных по спискам - целые числа, дробные числа и строки.");
        for (String dataString : dataList) {
            if (isMatcher(dataString, intRegexp)) {
                integersList.add(Long.parseLong(dataString));
            } else if (isMatcher(dataString, floatRegexp)) {
                floatsList.add(Float.parseFloat(dataString));
            } else {
                stringsList.add(dataString);
            }
        }
        log.debug("Фильтрация данных по типам - Финиш.");
    }

    private boolean isMatcher(String dataString, String regexp) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(dataString);
        return matcher.find();
    }

    private void isExistsStrings() throws NotFoundException {
        log.debug("Проверка наличия строк для последующей фильтрации по типу данных.");
        if (dataList.size() == 0) {
            System.err.println("В данных, полученных при обработке файлов, не была найдена ни одна строка. " +
                    "Программа закончила свою работу. Попробуйте повторить свой запрос с другими вводными данными.");
            log.error("В файлах пользователя отсутствуют данные для фильтрации");
            throw new NotFoundException("В пользовательских файлах отсутствуют данные для фильтрации по их типам.");
        }
    }

}