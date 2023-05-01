package org.utn.presentacion.carga_incidentes;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

public class CsvReader {
    private static final Set<String> HEADERS = new HashSet<>(Arrays.asList(
            "Codigo de catalogo", "Fecha de reporte", "Descripcion", "Estado", "Operador", "Persona que lo reporto", "Fecha cierre", "Motivo rechazo"
    ));

    public static void main(String[] args) throws IOException, CsvException {
        //TODO Actualmente se harcodeo el file name pero deberia ser ingresado por parametro

        String fileName = "prueba.csv";
        String file_path = "src/main/resources/"+fileName;

        Reader reader = new FileReader(file_path);
        CSVParser csvParser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();

        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(0)
                .withCSVParser(csvParser)
                .build();

        String[] headers = csvReader.readNext();

        checkHeaders(headers);

        //Recorro cada linea del archivo CSV
        String[] record;

        int incidenciasCargadas = 0;
        while ((record = csvReader.readNext()) != null) {
            GestorIncidencias gestor =new GestorIncidencias();
            try {
                gestor.procesarLinea(record);
                incidenciasCargadas++;
                //SE CREO LA INCIDENCIA DE MANERA EXITOSA
            } catch (Exception e) {
                //FALLO LA CREACION DE LA INCIDENCIA
                System.err.println(e.getMessage());
            }
        }
        //Finalizo la lectura del CSV
        csvReader.close();
    }

    private static void checkHeaders(String[] headers){
        //TODO VALIDAR HEADERS
        /*Set<String> headerSet = new HashSet<>(Arrays.asList(headers));
        Set<String> missingHeaders = new HashSet<>(HEADERS);
        missingHeaders.removeAll(headerSet);
        if (!missingHeaders.isEmpty()) {
            System.out.println("El encabezado del archivo CSV no contiene los siguientes encabezados: " + missingHeaders);
            String test = (String) missingHeaders.toArray()[0];
            System.out.println("La variable:"+test+" tiene " + test.length() + " caracteres. El primer caracter es: "+ StringUtils.substring(test, 0, 1));
        }*/

    }
}

