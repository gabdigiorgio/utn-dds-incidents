package org.utn.dominio.carga_incidentes;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
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
    List<Boolean> line_status = new ArrayList<Boolean>();

    // Metodo trivial para saber cuantas lineas se cargaron, cuantas tuvieron problemas y cuantas no
    public void csvStats(){
        System.out.println("La cantidad de filas del csv ingresado son: "+line_status.size());

        try {
            int true_conts = 0;
            int false_conts = 0;

            for (int n = 0; n < line_status.size(); n++) {
                if (line_status.get(n)) {
                    true_conts += 1;
                } else {
                    false_conts += 1;
                }
            }
            System.out.println("La cantidad de filas sin error " + true_conts);
            System.out.println("La cantidad de filas con error " + false_conts);
        }
        catch (Exception e){
            System.err.println(e);
        }
    }

    // Con esto armariamos una lista de estados para determinar la cantidad de filas del csv y si tuvo errores
    public void addLineStatus(Boolean status){
        this.line_status.add(status);
    }

   public IncidentCSVParser(String filename) {
       this.fileName = filename;
   }

    public void readFile(BufferedReader reader) {
        boolean isHeader = true;
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                String[] list = line.split("\t");

                // We validate all headers are present and get its indexes
                if (isHeader) {
                    boolean isValidHeaders = getIndexHeaders(list);
                    if (!isValidHeaders) throw new IOException("[ERROR] El archivo no posee encabezados validos");

                    isHeader = false;
                } else {
                    System.out.println("Analizando la linea : "+line_status.size()+" "+line);
                    boolean isValidIncident = validateRowIncident(list);
                    addLineStatus(isValidIncident);
                }
            }
            csvStats();
        } catch (IOException e) {   // TODO revisar este camino de ejecución
            System.err.println("Error al leer linea" + e);
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        dateFormat.setLenient(false);

        try{
            // TODO armar metodos para encontrar errores particulares
            // Validate contains non-nullable data
            if (row[indexCode].trim().equals("")) return false;
            if (row[indexReportDate].trim().equals("")) return false;
            if (row[indexDescription].trim().equals("")) return false;
            if (row[indexStatus].trim().equals("")) return false;

            // TODO validar el estado ingresado será "Abierto" -> tiene sentido? Los importa un 3°
            if (row[indexStatus].trim().equals("Abierto") && !row[indexResolvedDate].trim().equals("")) return false;

            dateFormat.parse(row[indexReportDate]);
        } catch(Exception e) {
            System.err.println(e);
           return false;
        }
        return true;
    }
}
