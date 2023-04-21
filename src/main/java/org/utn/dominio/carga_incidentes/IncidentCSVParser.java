package org.utn.dominio.carga_incidentes;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
                    if (!isValidHeaders){
                        System.err.println("[ERROR] El archivo no posee encabezados validos");
                    }

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
    /*
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
    */

    private boolean validateNullRow(String[] row){
        int cont = 0;

        for (int iter = 0; iter < row.length-4; iter ++){   // los 4 primeros campos NO pueden estar vacios...
            if(!row[iter].trim().equals("")){
                cont ++;
            }
        }

        return (cont < 4 && !row[0].equals(""));
    }

    private boolean validateOpenStatus(String[] row){
        return !row[indexStatus].trim().equals("Abierto");
    }

    private boolean validateIndexCode(String[] row){

        boolean status = true;
        char token = '-';

        if (row[indexCode].length() != 7){
            System.err.println("[ERROR] El codigo de incidencia es mayor o menor a 7 dígitos");
            status = false;
        }else if (!(row[indexCode].charAt(4) == token)){
            System.err.println("[ERROR] El codigo de incidencia no respeta la codificación XXXX-XX");
            status = false;
        }

        return status;
    }

    private boolean validateReportDate(String[] row){

        boolean status = true;

        if (row[indexReportDate].trim().equals("")){
            System.err.println("[ERROR] La fecha de reporte se encuentra vacia...");
            status = false;
        }

        return status;
    }

    private boolean validateDescription(String[] row){

        boolean status = true;

        if (row[indexDescription].trim().equals("")){
            System.err.println("[ERROR] La descripción se encuentra vacia...");
            status = false;
        }

        return status;
    }

    private boolean validateIndexStatus(String[] row){

        boolean status = true;

        if (row[indexStatus].trim().equals("")){
            System.err.println("[ERROR] El index se encuentra vacio...");
            status = false;
        }

        return status;
    }

    private boolean validateRowIncident(String[] row) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        dateFormat.setLenient(false);
        boolean[] status_array = new boolean[4];
        int status_checker = 0;

        try{
            // TODO armar metodos para encontrar errores particulares
            // Validate contains non-nullable data
            if(validateNullRow(row)){
                // TODO validar el estado ingresado será "Abierto" -> tiene sentido? Los importa un 3°
                // validateOpenStatus(row); No dice si hay que validar si esta abierto o cerrado
                TimeUnit.MILLISECONDS.sleep(200);
                status_array[0] = validateIndexCode(row);
                TimeUnit.MILLISECONDS.sleep(200);
                status_array[1] =validateReportDate(row);
                TimeUnit.MILLISECONDS.sleep(200);
                status_array[2] =validateDescription(row);
                TimeUnit.MILLISECONDS.sleep(200);
                status_array[3] =validateIndexStatus(row);

            }else{
                TimeUnit.MILLISECONDS.sleep(500);
                System.err.println("[ERROR] La fila se encuentra vacia...");
                return false;
            }
        } catch(Exception e) {
            System.err.println("[ERROR] Inesperado : "+e);
           return false;
        }

        for (boolean b : status_array) {
            if (!b) {
                status_checker++;
            }
        }

        return status_checker == 0;
    }
}
