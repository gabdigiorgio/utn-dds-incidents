package org.utn.presentacion.carga_incidentes;

import org.utn.utils.StringValidatorUtils;
import org.utn.utils.exceptions.validador.DatosIncompletosException;
import org.utn.utils.exceptions.validador.EstadoInvalidoException;
import org.utn.utils.exceptions.validador.FormatoFechaInvalidaException;

import java.util.stream.Stream;

public class Validador {

    public static void validar(String codigoCatalogo,
                               String fechaReporte,
                               String descripcion,
                               String estado,
                               String operador,
                               String personaReporto,
                               String fechaCierre,
                               String motivoRechazo) throws Exception {
        // SE VALIDA QUE LOS CAMPOS MANDATORIOS NO ESTEN VACIOS
        if (Stream.of(codigoCatalogo, fechaReporte, descripcion, estado).anyMatch(String::isEmpty)) {
            throw new DatosIncompletosException();
        }

        validadorDeFormatos(fechaReporte, estado, fechaCierre);

        switch (estado) {
            case "Reportado" -> {
                if (!motivoRechazo.isEmpty()) {
                    throw new Exception("No es posible crear una incidencia con estado Reportado y con un motivo de rechazo");
                }
            }
            case "Desestimado" -> {
                if (motivoRechazo.isEmpty()) {
                    throw new Exception("No es posible crear una incidencia con estado Desestimado sin motivo de rechazo");
                }
            }
        }
    }

    private static void validadorDeFormatos(String fechaReporte, String estado,String fechaCierre) throws Exception {
        //VALIDA CON EXPRESIONES REGULARES QUE LOS FORMATOS INGRESADOS SEAN CORRECTOS
        if (!StringValidatorUtils.isFecha(fechaReporte)) throw new FormatoFechaInvalidaException(fechaReporte);
        if (!StringValidatorUtils.isUserState(estado)) throw new EstadoInvalidoException(estado);

        if (!fechaCierre.isEmpty()){
            if (!StringValidatorUtils.isFecha(fechaCierre)) throw new FormatoFechaInvalidaException(fechaCierre);
        }
    }

}