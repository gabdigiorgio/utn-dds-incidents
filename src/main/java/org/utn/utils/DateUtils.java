package org.utn.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static LocalDate parseDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        if (!stringDate.isEmpty()) {
            LocalDate fecha = LocalDate.parse(stringDate, formatter);
            return fecha;
        } else {
            return null;
        }
    }
}
