package org.utn.utils;

import org.utn.utils.exceptions.validator.InvalidDateException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {
    public static LocalDate parseDate(String stringDate) throws InvalidDateException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        try{
            if (!stringDate.isEmpty()) {
                LocalDate fecha = LocalDate.parse(stringDate, formatter);
                return fecha;
            } else {
                return null;
            }
        } catch (DateTimeParseException exception) {
            throw new InvalidDateException(stringDate);
        }

    }
}
