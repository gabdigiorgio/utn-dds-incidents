package org.utn.domain.incident;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StateConverter implements AttributeConverter<State, String> {
    @Override
    public String convertToDatabaseColumn(State state) {
        return state.toString();
    }

    @Override
    public State convertToEntityAttribute(String dbData) {
        State state = null;
        switch (dbData) {
            case "Asignado" -> state = State.ASSIGNED;
            case "Confirmado" -> state = State.CONFIRMED;
            case "Desestimado" -> state = State.DISMISSED;
            case "En progreso" -> state = State.IN_PROGRESS;
            case "Reportado" -> state = State.REPORTED;
            case "Solucionado" -> state = State.RESOLVED;
        }
        return state;
    }
}