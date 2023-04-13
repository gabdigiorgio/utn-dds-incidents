package org.utn.carga_incidentes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LecturaCSV {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el nombre del archivo: ");

        String fileName = scanner.nextLine();
        String projectDir = System.getProperty("user.dir");
        String filePath = projectDir +"/src/main/resources/"+fileName;



        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            /*
            EN ESTE PUNTO LLAMO AL CSV PARSER PASANDOLE LA VARIABLE (reader) EL CUAL CONTIENE EL TEXTO DEL CSV
            EL CSV PARSER DEBERIA PARSEAR LA LINEA Y GUARDAR EL INCIDENTE LLAMANDO AL REPO INCIDENTE
            */
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo " + fileName + ": " + e.getMessage());
            System.err.println("El path es " + projectDir );
        }
    }

}

