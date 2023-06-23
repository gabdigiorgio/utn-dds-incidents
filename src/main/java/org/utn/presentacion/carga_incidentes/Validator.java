package org.utn.presentacion.carga_incidentes;

import org.utn.utils.StringValidatorUtils;
import org.utn.utils.exceptions.validador.IncompleteDataException;
import org.utn.utils.exceptions.validador.InvalidStatusException;
import org.utn.utils.exceptions.validador.InvalidDateFormatException;

import java.util.stream.Stream;

public class Validator {

    public static void validate(
            String catalogCode,
            String reportDate,
            String description,
            String status,
            String operator,
            String whoReported,
            String closeDate,
            String rejectionReason
    ) throws Exception {
        // SE VALIDA QUE LOS CAMPOS MANDATORIOS NO ESTEN VACIOS
        if (Stream.of(catalogCode, reportDate, description, status).anyMatch(String::isEmpty)) {
            throw new IncompleteDataException();
        }

        formatValidator(reportDate, status, closeDate);

        switch (status) {
            case "Reportado" -> {
                if (!rejectionReason.isEmpty()) {
                    throw new Exception("No es posible crear una incidencia con estado Reportado y con un motivo de rechazo");
                }
            }
            case "Desestimado" -> {
                if (rejectionReason.isEmpty()) {
                    throw new Exception("No es posible crear una incidencia con estado Desestimado sin motivo de rechazo");
                }
            }
        }
    }

    private static void formatValidator(String reportDate, String status, String closeDate) throws Exception {
        //VALIDA CON EXPRESIONES REGULARES QUE LOS FORMATOS INGRESADOS SEAN CORRECTOS
        if (!StringValidatorUtils.isDate(reportDate)) throw new InvalidDateFormatException(reportDate);
        if (!StringValidatorUtils.isUserStatus(status)) throw new InvalidStatusException(status);

        if (!closeDate.isEmpty()){
            if (!StringValidatorUtils.isDate(closeDate)) throw new InvalidDateFormatException(closeDate);
        }
    }

}