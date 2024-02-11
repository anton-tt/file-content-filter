package titov.managers;

import lombok.Data;
import lombok.NonNull;
import titov.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
//@Slf4j
public class StringsManager {
    private List<Long> integersList = new ArrayList<>();
    private List<Float> floatsList = new ArrayList<>();
    private List<String> stringsList = new ArrayList<>();
    @NonNull
    private final List<String> dataList;

    String intRegexp = "^[+-]?\\d+$";
    String floatRegexp = "^[+-]?\\d+\\.\\d+((e|E)(\\-|\\+)?\\d+)?$";

    public void filterStrings() {
        try {
            isExistsStrings();
        } catch (NotFoundException e) {  // ЧИСЛА с F b L и прочее!!!
            e.printStackTrace();
        }

        for (String dataString : dataList) {
            if (isMatcher(dataString, intRegexp)) {
                integersList.add(Long.parseLong(dataString));
            } else if (isMatcher(dataString, floatRegexp)) {
                floatsList.add(Float.parseFloat(dataString));
            } else {
                stringsList.add(dataString);
            }
        }
        System.out.println(integersList);
        Collections.sort(integersList);
        System.out.println(integersList);
        System.out.println(floatsList);
        Collections.sort(floatsList);
        System.out.println(floatsList);
        System.out.println(stringsList);
        Collections.sort(stringsList);
        System.out.println(stringsList);
    }

    private boolean isMatcher(String dataString, String regexp) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(dataString);
        return matcher.find();
    }

    private void isExistsStrings() throws NotFoundException {
        if (dataList.size() == 0) {
            throw new NotFoundException("В данных, полученных при обработке файлов, не была найдена ни одина строка. " +
                    "Программа закончила свою работу. Попробуйте повторить свой запрос с другими вводными данными.");
        }
    }

}