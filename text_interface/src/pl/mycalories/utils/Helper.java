package pl.mycalories.utils;

import java.util.Map;
import java.util.Objects;

public class Helper {

    // max 4 digits
    public static final String INPUT_NUMBERS_REGEX = "^\\d{0,4}$";

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
