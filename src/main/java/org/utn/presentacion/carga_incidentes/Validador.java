package org.utn.presentacion.carga_incidentes;

import org.utn.exceptions.validador.EstadoInvalidoException;

public class Validador {

    public static void validarEstado(String estado, String motivoRechazo) throws Exception {
        switch (estado) {
            case "Reportado":
                if (!motivoRechazo.isEmpty()){
                    throw new Exception("No es posible crear una incidencia con estado Reportdo y un motivo de rechazo");
                }
            case "Desestimado":
                if (motivoRechazo.isEmpty()){
                    throw new Exception("No es posible crear una incidencia con estado Desestimado sin motivo de rechazo");
                }
            default:
                throw new EstadoInvalidoException(estado);
        }
    }

    // Otros métodos de validación
}