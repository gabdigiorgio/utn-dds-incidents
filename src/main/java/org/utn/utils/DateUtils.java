package org.utn.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
  public static LocalDate parsearFecha(String fechaString) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
    if (!fechaString.isEmpty()) {
      LocalDate fecha = LocalDate.parse(fechaString, formatter);
      return fecha;
    } else {
      return null;
    }
  }
}
