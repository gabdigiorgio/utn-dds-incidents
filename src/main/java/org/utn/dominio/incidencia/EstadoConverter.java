package org.utn.dominio.incidencia;

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
        switch (dbData) {
            case "Asignado" -> estado = EnumEstado.ASIGNADO;
            case "Confirmado" -> estado = EnumEstado.CONFIRMADO;
            case "Desestimado" -> estado = EnumEstado.DESESTIMADO;
            case "EnProgreso" -> estado = EnumEstado.EN_PROGRESO;
            case "Reportado" -> estado = EnumEstado.REPORTADO;
            case "Solucionado" -> estado = EnumEstado.SOLUCIONADO;
        }
        return estado;
    }
}