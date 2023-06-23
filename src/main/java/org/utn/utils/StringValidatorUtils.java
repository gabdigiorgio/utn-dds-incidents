package org.utn.utils;

import java.util.regex.Pattern;

public class StringValidatorUtils {

    public static boolean isNumber(String s) {
        if (s == null || s.equals("")) {
            return false;
        }
        return s.chars().allMatch(Character::isDigit);
    }

    public static boolean isCodePlaceFormat(String s) {
        String regex = "^[a-zA-Z0-9]{4}-[a-zA-Z0-9]{2}$";
        return validateRegularExpression(s, regex);
    }

    public static boolean isUserStatus(String s) {
        return validateRegularExpression(s, "^(Asignado|Confirmado|Desestimado|En progreso|Reportado|Solucionado)$");
    }

    public static boolean isCatalogCode(String catalogCode) {
        return validateRegularExpression(catalogCode, "^[a-zA-Z0-9]{4}-[a-zA-Z0-9]{2}$");
    }

    public static boolean isDate(String date) throws Exception {
        return validateRegularExpression(date, "^(0?[1-9]|[12][0-9]|3[01])(0?[1-9]|1[0-2])[0-9]{4}$");
    }

    private static boolean validateRegularExpression(String valor, String re) {
        Pattern regex = Pattern.compile(re);
        return regex.matcher(valor).matches();
    }

}
