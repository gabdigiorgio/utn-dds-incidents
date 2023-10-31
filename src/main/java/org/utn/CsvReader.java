package org.utn;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.opencsv.exceptions.CsvException;
import org.utn.persistence.PersistenceUtils;

import javax.persistence.EntityManagerFactory;

public class CsvReader {
    public static void main(String[] args) throws IOException, CsvException {
        String fileName = args[0]; //Se recibe el nombre del archivo como argumento
        String file_path = "src/main/resources/"+fileName;
        EntityManagerFactory entityManagerFactory =  PersistenceUtils.createEntityManagerFactory();

        //VALIDA QUE EL ARCHIVO EXISTA
        if ( ! Files.exists( Paths.get(file_path) )) {
            System.err.println("'" + file_path + "' no existe...");
            System.exit(1);
        }
        System.out.println(new org.utn.presentation.incidents_load.CsvReader(entityManagerFactory.createEntityManager()).execute(file_path));
    }
}

