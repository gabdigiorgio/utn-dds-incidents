package org.utn;

import com.opencsv.exceptions.CsvException;
import org.utn.modules.IncidentManagerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CsvReader {
    public static void main(String[] args) throws IOException, CsvException {
        String fileName = args[0]; //Se recibe el nombre del archivo como argumento
        String file_path = "src/main/resources/"+fileName;

        //VALIDA QUE EL ARCHIVO EXISTA
        if ( ! Files.exists( Paths.get(file_path) )) {
            System.err.println("'" + file_path + "' no existe...");
            System.exit(1);
        }
        System.out.println(new org.utn.presentation.incidents_load.CsvReader(IncidentManagerFactory.createIncidentManager()).execute(file_path));
    }
}

