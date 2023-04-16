package org.utn.dominio.carga_incidentes;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class IncidentCSV {

    // Metodo trivial para saber si el archivo existe o no
    public static Boolean checkFileExistance(File file_to_find) {
        return file_to_find.isFile();
    }

    // Segundo metodo trivial, este valida que el archivo sea un csv al leer su extension
    public static Boolean checkFileExtension(String path){
        return path.substring(path.length() - 3).equals("csv");
    }

    // Nos fijamos si existe el archivo y si es un csv -> después nos fijamos si es valido o no como incidencia
    public static Boolean sourceValidation(String csv_path){
        return checkFileExistance(new File(csv_path)) && checkFileExtension(csv_path);
    }

    public static void userInteraction(){
        try{

            int tries = 0;
            Scanner input = new Scanner(System.in);

            do{
                System.out.print("Por favor, ingrese el nombre del archivo de incidencias : ");
                String fileName = input.nextLine();

                String file_path = "src/main/resources/"+fileName;

                if(sourceValidation(file_path)){
                    System.out.println("Archivo valido ! ! ! ! ");
                    // Acá debe parsear el csv

                    IncidentCSVParser parser = new IncidentCSVParser(fileName);
                    BufferedReader reader = new BufferedReader(new FileReader(file_path));
                    parser.readFile(reader);

                    System.out.print("Desea cargar otro archivo de incidencias ? [S: si | N: no ] : ");
                    String decision = input.nextLine();

                    if (decision.equals("S") || decision.equals("s")){
                        userInteraction();    // un poquito de recursion
                        break;
                    }
                    else {
                        tries += 5; // rompemos el while para salir en limpio
                    }

                }
                else{
                    System.out.println("[ERROR] : Archivo no encontrado / formato incorrecto !");
                    System.out.println("Intentos restantes : "+(4-tries));
                    tries += 1;
                }
            }while (tries < 5 );    // Numero arbitrario, le di 5 de buena gente
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        /*
        // Se crea un Scanner (clase que permite ingresar datos de tipos primitivos)
        Scanner scanner = new Scanner(System.in);   // Mucha imaginación no tenemos porque se llama scanner...

        System.out.print("Ingrese la ruta del archivo: ");

        String fileName = scanner.nextLine();
        System.out.print(sourceValidation(fileName));
        */

        userInteraction();
        /*String projectDir = System.getProperty("user.dir");

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            IncidentCSVParser parser = new IncidentCSVParser(fileName);
            parser.readFile(reader);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo " + fileName + " : " + e.getMessage());
            System.err.println("El path es " + projectDir );
            scanner.close();
        }
        scanner.close();*/
    }

}

