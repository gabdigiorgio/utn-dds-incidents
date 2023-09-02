package org.utn.presentation.incidents_load;

import org.utn.utils.StringValidatorUtils;
import org.utn.utils.exceptions.validator.IncompleteDataException;
import org.utn.utils.exceptions.validator.InvalidStateException;
import org.utn.utils.exceptions.validator.InvalidDateException;

import java.util.stream.Stream;

public class Validator {

    public static void validate(String catalogCode,
                                String reportDate,
                                String description,
                                String state,
                                String operator,
                                String reportedBy,
                                String closingDate,
                                String rejectedReason) throws Exception {
        // SE VALIDA QUE LOS CAMPOS MANDATORIOS NO ESTEN VACIOS
        if (Stream.of(catalogCode, reportDate, description, state).anyMatch(String::isEmpty)) {
            throw new IncompleteDataException();
        }

        formatValidator(reportDate, state, closingDate);

        switch (state) {
            case "Reportado" -> {
                if (!rejectedReason.isEmpty()) {
                    throw new Exception("No es posible crear una incidencia con estado Reportado y con un motivo de rechazo");
                }
            }
            case "Desestimado" -> {
                if (rejectedReason.isEmpty()) {
                    throw new Exception("No es posible crear una incidencia con estado Desestimado sin motivo de rechazo");
                }
            }
        }
    }

    private static void formatValidator(String reportDate, String state, String closingDate) throws Exception {
        //VALIDA CON EXPRESIONES REGULARES QUE LOS FORMATOS INGRESADOS SEAN CORRECTOS
        if (!StringValidatorUtils.isDate(reportDate)) throw new InvalidDateException(reportDate);
        if (!StringValidatorUtils.isUserState(state)) throw new InvalidStateException(state);

        if (!closingDate.isEmpty()){
            if (!StringValidatorUtils.isDate(closingDate)) throw new InvalidDateException(closingDate);
        }
    }

}