package org.utn.dominio.carga_incidentes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class LecturaCSV {
    public static void main(String[] args) {
        Integer indexCode;
        Integer indexReportDate;
        Integer indexDescription;
        Integer indexStatus;
        Integer indexOperator;
        Integer indexReporterName;
        Integer indexResolvedDate;
        Integer indexRejectedReason;

        String headerCode = "Codigo de catalogo";
        String headerReportDate = "Fecha de reporte";
        String headerDescription = "Descripcion";
        String headerStatus = "Estado";
        String headerOperator = "Operador";
        String headerReporterName = "Persona que lo reporto";
        String headerResolvedDate = "Fecha cierre";
        String headerRejectedReason = "Motivo rechazo";

        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la ruta del archivo: ");

        String fileName = scanner.nextLine();
        String projectDir = System.getProperty("user.dir");

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            /*
            EN ESTE PUNTO LLAMO AL CSV PARSER PASANDOLE LA VARIABLE (reader) EL CUAL CONTIENE EL TEXTO DEL CSV
            EL CSV PARSER DEBERIA PARSEAR LA LINEA Y GUARDAR EL INCIDENTE LLAMANDO AL REPO INCIDENTE
            */
            Boolean isHeader = true;
            String line;
            while ((line = reader.readLine()) != null) {
                String[] list = line.split("\t");

                // We validate all headers are present and get its indexs
                if (isHeader) {
                    indexCode = IntStream.range(0, list.length)
                            .filter(index -> headerCode.equals(list[index]))
                            .findFirst()
                            .orElse(-1);
                    indexReportDate = IntStream.range(0, list.length)
                            .filter(index -> headerReportDate.equals(list[index]))
                            .findFirst()
                            .orElse(-1);
                    indexDescription = IntStream.range(0, list.length)
                            .filter(index -> headerDescription.equals(list[index]))
                            .findFirst()
                            .orElse(-1);
                    indexStatus = IntStream.range(0, list.length)
                            .filter(index -> headerStatus.equals(list[index]))
                            .findFirst()
                            .orElse(-1);
                    indexOperator = IntStream.range(0, list.length)
                            .filter(index -> headerOperator.equals(list[index]))
                            .findFirst()
                            .orElse(-1);
                    indexReporterName = IntStream.range(0, list.length)
                            .filter(index -> headerReporterName.equals(list[index]))
                            .findFirst()
                            .orElse(-1);
                    indexResolvedDate = IntStream.range(0, list.length)
                            .filter(index -> headerResolvedDate.equals(list[index]))
                            .findFirst()
                            .orElse(-1);
                    indexRejectedReason = IntStream.range(0, list.length)
                            .filter(index -> headerRejectedReason.equals(list[index]))
                            .findFirst()
                            .orElse(-1);

                    List<Integer> allIndexs = Arrays.asList(indexCode, indexReportDate, indexDescription, indexStatus, indexOperator, indexReporterName, indexResolvedDate, indexRejectedReason);
                    if (allIndexs.contains(-1)) throw new IOException("Archivo incompleto");

                    System.out.println("indice del codigo" + indexCode);

                    isHeader = false;
                } else {

                }
                System.out.println(line);
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo " + fileName + " : " + e.getMessage());
            System.err.println("El path es " + projectDir );
            scanner.close();
        }
        scanner.close();
    }

}

