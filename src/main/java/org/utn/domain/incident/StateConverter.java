package org.utn.domain.incident;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StateConverter implements AttributeConverter<State, String> {
    @Override
    public String convertToDatabaseColumn(State state) {
        return state.getStateName();
    }

    @Override
    public State convertToEntityAttribute(String dbData) {
        State state = null;
        switch (dbData) {
            case "Asignado" -> state = StateEnum.ASSIGNED;
            case "Confirmado" -> state = StateEnum.CONFIRMED;
            case "Desestimado" -> state = StateEnum.DISMISSED;
            case "EnProgreso" -> state = StateEnum.IN_PROGRESS;
            case "Reportado" -> state = StateEnum.REPORTED;
            case "Solucionado" -> state = StateEnum.RESOLVED;
        }
        return state;
    }
}