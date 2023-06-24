package org.utn.dominio.estado;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EstadoConverter implements AttributeConverter<Estado, String> {
    @Override
    public String convertToDatabaseColumn(Estado estado) {
        return estado.getNombreEstado();
    }

    @Override
    public Estado convertToEntityAttribute(String dbData) {
        Estado estado = null;
        switch (dbData){
            case "Asignado" -> estado = new Asignado();
            case "Confirmado" -> estado = new Confirmado();
            case "Desestimado" -> estado = new Desestimado();
            case "EnProgreso" -> estado = new EnProgreso();
            case "Reportado" -> estado = new Reportado();
            case "Solucionado" -> estado = new Solucionado();
        }
        return estado;
    }
}
