package org.utn.presentacion.carga_incidentes;

import org.utn.exceptions.validador.DatosIncompletosException;
import org.utn.exceptions.validador.EstadoInvalidoException;
import org.utn.exceptions.validador.FormatoCodigoCatalogInvalidoException;
import org.utn.exceptions.validador.FormatoFechaInvalidaException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Validador {

    public static void validar(String codigoCatalogo, String fechaReporte, String descripcion, String estado, String operador, String personaReporto, String fechaCierre, String motivoRechazo) throws Exception {
        // SE VALIDA QUE LOS CAMPOS MANDATORIOS NO ESTEN VACIOS
        if (Stream.of(codigoCatalogo, fechaReporte, descripcion, estado).anyMatch(String::isEmpty)) {
            throw new DatosIncompletosException();
        }

        validadorDeFormatos(codigoCatalogo,fechaReporte,estado,fechaCierre);

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

    private static void validadorDeFormatos(String codigoCatalogo, String fechaReporte, String estado,String fechaCierre) throws Exception {
        //VALIDA CON EXPRESIONES REGULARES QUE LOS FORMATOS INGRESADOS SEAN CORRECTOS
        validarFormatoCodigoCatalogo(codigoCatalogo);
        validarFormatoFecha(fechaReporte);
        validarFormatoEstado(estado);
        if (!fechaCierre.isEmpty()){
            validarFormatoFecha(fechaReporte);
        }
    }

    private static void validarFormatoCodigoCatalogo(String codigoCatalogo) throws Exception {
        validarExpresionRegular(codigoCatalogo, "^[a-zA-Z0-9]{4}-[a-zA-Z0-9]{2}$",
                FormatoCodigoCatalogInvalidoException.class);
    }

    private static void validarFormatoFecha(String fecha) throws Exception {
        validarExpresionRegular(fecha, "^(0?[1-9]|[12][0-9]|3[01])(0?[1-9]|1[0-2])[0-9]{4}$",
                FormatoFechaInvalidaException.class);
    }
    private static void validarFormatoEstado(String estado) throws Exception {
        validarExpresionRegular(estado, "^(Asignado|Confirmado|Desestimado|En progreso|Reportado|Solucionado)$",
                EstadoInvalidoException.class);
    }

    private static void validarExpresionRegular(String valor, String expresionRegular,Class<? extends Exception> excepcion) throws Exception {
        Pattern regex = Pattern.compile(expresionRegular);
        Matcher matcher = regex.matcher(valor);
        if (!matcher.matches()) {
            throw excepcion.getConstructor(String.class).newInstance(valor);
        }
    }

}