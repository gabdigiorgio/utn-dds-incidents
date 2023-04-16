package org.utn.dominio.carga_incidentes;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.text.SimpleDateFormat;

public class IncidentCSVParser {
    String fileName;
    int indexCode;
    int indexReportDate;
    int indexDescription;
    int indexStatus;
    int indexOperator;
    int indexReporterName;
    int indexResolvedDate;
    int indexRejectedReason;

   public IncidentCSVParser(String filename) {
       this.fileName = filename;
   }

    public Boolean readFile(BufferedReader reader) {
        boolean isHeader = true;
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] list = line.split("\t");

                // We validate all headers are present and get its indexs
                if (isHeader) {
                    boolean isValidHeaders = getIndexHeaders(list);
                    if (!isValidHeaders) throw new IOException("Archivo incompleto");

                    isHeader = false;
                } else {
                    boolean isValidIncident = validateRowIncident(list);
                }
                System.out.println(line);
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error al leer linea" + e);
            return false;
        }
    }
    private boolean getIndexHeaders(String[] headers) {
        indexCode = IntStream.range(0, headers.length)
                .filter(index -> IncidentCSVHeaders.CODE.get().equals(headers[index]))
                .findFirst()
                .orElse(-1);
        indexReportDate = IntStream.range(0, headers.length)
                .filter(index -> IncidentCSVHeaders.REPORT_DATE.get().equals(headers[index]))
                .findFirst()
                .orElse(-1);
        indexDescription = IntStream.range(0, headers.length)
                .filter(index -> IncidentCSVHeaders.DESCRIPTION.get().equals(headers[index]))
                .findFirst()
                .orElse(-1);
        indexStatus = IntStream.range(0, headers.length)
                .filter(index -> IncidentCSVHeaders.STATUS.get().equals(headers[index]))
                .findFirst()
                .orElse(-1);
        indexOperator = IntStream.range(0, headers.length)
                .filter(index -> IncidentCSVHeaders.OPERATOR.get().equals(headers[index]))
                .findFirst()
                .orElse(-1);
        indexReporterName = IntStream.range(0, headers.length)
                .filter(index -> IncidentCSVHeaders.REPORTER.get().equals(headers[index]))
                .findFirst()
                .orElse(-1);
        indexResolvedDate = IntStream.range(0, headers.length)
                .filter(index -> IncidentCSVHeaders.RESOLVED_DATE.get().equals(headers[index]))
                .findFirst()
                .orElse(-1);
        indexRejectedReason = IntStream.range(0, headers.length)
                .filter(index -> IncidentCSVHeaders.REJECTED_REASON.get().equals(headers[index]))
                .findFirst()
                .orElse(-1);

        List<Integer> allIndexes = Arrays.asList(indexCode, indexReportDate, indexDescription, indexStatus, indexOperator, indexReporterName, indexResolvedDate, indexRejectedReason);
        if (allIndexes.contains(-1)) return false;
        return true;
    }

    private boolean validateRowIncident(String[] row) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddmmaaaa");
        dateFormat.setLenient(false);

        // Validate contains non-nullable data
        if (row[indexCode].trim().equals("")) return false;
        if (row[indexReportDate].trim().equals("")) return false;
        if (row[indexDescription].trim().equals("")) return false;
        if (row[indexStatus].trim().equals("")) return false;

        // TODO validar el estado ingresado será "Abierto"
        if (row[indexStatus].trim().equals("Abierto") && !row[indexResolvedDate].trim().equals("")) return false;

        // Validate formats
        // TODO toma fecha errónea 15042023
        try {
           dateFormat.parse(row[indexReportDate]);
        } catch(ParseException e) {
           System.err.println("Fecha invalida: " + row[indexReportDate] + e);
           return false;
        }
        return true;
    }
}
