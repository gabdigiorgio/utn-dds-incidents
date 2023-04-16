package org.utn.dominio.carga_incidentes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class IncidentCSV {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la ruta del archivo: ");

        String fileName = scanner.nextLine();
        String projectDir = System.getProperty("user.dir");

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            IncidentCSVParser parser = new IncidentCSVParser(fileName);
            parser.readFile(reader);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo " + fileName + " : " + e.getMessage());
            System.err.println("El path es " + projectDir );
            scanner.close();
        }
        scanner.close();
    }

}

