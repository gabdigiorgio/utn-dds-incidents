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
        return validarExpresionRegular(s, regex);
    }

    public static boolean isUserState(String s) {
        return validarExpresionRegular(s, "^(Asignado|Confirmado|Desestimado|En progreso|Reportado|Solucionado)$");
    }

    public static boolean isCodigoCatalogo(String codigoCatalogo) {
        return validarExpresionRegular(codigoCatalogo, "^[a-zA-Z0-9]{4}-[a-zA-Z0-9]{2}$");
    }

    public static boolean isFecha(String fecha) throws Exception {
        return validarExpresionRegular(fecha, "^(0?[1-9]|[12][0-9]|3[01])(0?[1-9]|1[0-2])[0-9]{4}$");
    }

    private static boolean validarExpresionRegular(String valor, String expresionRegular) {
        Pattern regex = Pattern.compile(expresionRegular);
        return regex.matcher(valor).matches();
    }

}
