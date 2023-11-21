package org.utn.presentation.incidents_load;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.utn.application.IncidentManager;
import org.utn.utils.DateUtils;

import java.io.FileReader;
import java.io.Reader;
import java.util.*;

public class CsvReader {

    private final IncidentManager manager;

    public CsvReader(IncidentManager manager) {
        this.manager = manager;
    }

    private static final Set<String> HEADERS = new HashSet<>(Arrays.asList(
            "Codigo de catalogo", "Fecha de reporte", "Descripcion", "Estado", "Operador", "Persona que lo reporto", "Fecha cierre", "Motivo rechazo"
    ));

    public String execute(String file_path) throws Exception {
        //SE HACE LA LECTURA DEL ARCHIVO
        Reader reader = new FileReader(file_path);
        return processFile(reader);
    }

    public String execute(Reader reader) throws Exception {
        return processFile(reader);
    }

    private String processFile(Reader reader) throws Exception {

        CSVParser csvParser = new CSVParserBuilder()
                .withSeparator('\t')
                .withIgnoreLeadingWhiteSpace(true)
                .withIgnoreQuotations(true)
                .build();

        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(0)
                .withCSVParser(csvParser)
                .build();

        //SE OBTIENEN LOS HEADERS Y SE BORRA EL CARACTER DE INICIO DE ARCHIVO EN CASO DE QUE EXISTA
        String[] headers = csvReader.readNext();
        deleteCharacterBOM(headers);

        //VALIDA QUE SE INGRESEN TODOS LOS HEADERS NECESARIOS
        try {
            checkHeaders(headers);
        } catch (Exception e) {
            csvReader.close();
            throw new RuntimeException(e);
        }

        //SE HACE UN MAPEO DE LOS HEADERS SEGÚN LA POSICIÓN INGRESADA EN EL CSV
        Map<String, Integer> headerMap = new HashMap<>();
        for (int i = 0; i < headers.length; i++) {
            headerMap.put(headers[i], i);
        }

        String[] record;
        int incidentsLoaded = 0;

        //COMIENZA LA LECTURA DE CADA LINEA DEL CSV
        while ((record = csvReader.readNext()) != null) {
            if (record.length == 0 || Arrays.stream(record).allMatch(String::isEmpty)) {
                continue; // Salta las líneas vacías
            }

            try {
                //SE COMPLETA CON STRINGS VACIOS SI LA LINEA NO FUE INGRESADA CON TODOS LOS CAMPOS
                String[] filledRecord = Arrays.copyOf(record, 8);
                Arrays.fill(filledRecord, record.length, filledRecord.length, "");

                String catalogCode = filledRecord[headerMap.get("Codigo de catalogo")];
                String reportDate = filledRecord[headerMap.get("Fecha de reporte")];
                String description = filledRecord[headerMap.get("Descripcion")];
                String state = filledRecord[headerMap.get("Estado")];
                String operator = filledRecord[headerMap.get("Operador")];
                String reportedBy = filledRecord[headerMap.get("Persona que lo reporto")];
                String closingDate = filledRecord[headerMap.get("Fecha cierre")];
                String rejectedReason = filledRecord[headerMap.get("Motivo rechazo")];

                Validator.validate(catalogCode, reportDate, description, state, operator, reportedBy, closingDate, rejectedReason);
                manager.createIncident(catalogCode,
                        DateUtils.parseDate(reportDate),
                        description,
                        state,
                        operator,
                        reportedBy,
                        DateUtils.parseDate(closingDate),
                        rejectedReason
                );
                incidentsLoaded++;
                //SE CREO LA INCIDENCIA DE MANERA EXITOSA
            } catch (Exception e) {
                //FALLO LA CREACIÓN DE LA INCIDENCIA
                String msg = "No fue posible cargar la incidencia con estos datos: " + Arrays.toString(record);
                System.err.println(msg);
                throw new Exception(msg);
            }
        }
        return String.format("Se cargaron exitosamente %d incidencias", incidentsLoaded);

    }

    public static void checkHeaders(String[] headers) throws Exception {
        Set<String> headerSet = new HashSet<>(Arrays.asList(headers));
        Set<String> missingHeaders = new HashSet<>(HEADERS);
        missingHeaders.removeAll(headerSet);
        if (!missingHeaders.isEmpty()) {
            throw new Exception("El encabezado del archivo CSV no contiene los siguientes encabezados: " + missingHeaders);
        }
    }

    public static void deleteCharacterBOM(String[] headers) {
        if (headers[0].startsWith("\uFEFF")) {
            // Eliminar el BOM del primer campo
            headers[0] = headers[0].substring(1);
        }
    }


}
