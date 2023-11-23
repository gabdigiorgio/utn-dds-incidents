package org.utn.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class StringValidatorUtils {

    public static boolean isNumber(String s) {
        if (s == null || s.equals("")) {
            return false;
        }
        return s.chars().allMatch(Character::isDigit);
    }

    public static boolean isCodePlaceFormat(String code) {
        String regex = "^[a-zA-Z0-9]{4}-[a-zA-Z0-9]{2}$";
        return validateRegularExpression(code, regex);
    }

    public static boolean isUserState(String state) {
        return validateRegularExpression(state, "^(Asignado|Confirmado|Desestimado|En progreso|Reportado|Solucionado)$");
    }

    public static boolean isLine(String line) {
        return validateRegularExpression(line, "^(Linea A|Linea B|Linea C|Linea D|Linea E|Linea H)$");
    }


    public static boolean isCatalogCode(String catalogCode) {
        return validateRegularExpression(catalogCode, "^[a-zA-Z0-9]{4}-[a-zA-Z0-9]{2}$");
    }

    public static boolean isDate(String date) throws Exception {
        return validateRegularExpression(date, "^(0?[1-9]|[12][0-9]|3[01])(0?[1-9]|1[0-2])[0-9]{4}$");
    }

    private static boolean validateRegularExpression(String value, String regularExpression) {
        Pattern regex = Pattern.compile(regularExpression);
        return regex.matcher(value).matches();
    }

}
