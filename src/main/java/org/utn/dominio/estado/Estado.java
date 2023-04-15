package org.utn.dominio.estado;

import org.utn.dominio.incidente.Incidencia;
import org.utn.dominio.excepciones.constantesExepciones;
public interface Estado {
    String getNombreEstado();
    default void asignarEmpleado(Incidencia incidencia) throws Exception {
        String msgException = String.format(constantesExepciones.ERROR_TRANSICION_ASIGNAR_EMPLEADO, incidencia.getEstado().getNombreEstado());
        throw new Exception(msgException);
    }
    default void confirmarIncidencia(Incidencia incidencia) throws Exception {
        String msgException = String.format(constantesExepciones.ERROR_TRANSICION_ASIGNAR_EMPLEADO, incidencia.getEstado().getNombreEstado());
        throw new Exception(msgException);
    }
    default void desestimarIncidencia(Incidencia incidencia) throws Exception {
        String msgException = String.format(constantesExepciones.ERROR_TRANSICION_ASIGNAR_EMPLEADO, incidencia.getEstado().getNombreEstado());
        throw new Exception(msgException);
    }
    default void iniciarProgreso(Incidencia incidencia) throws Exception {
        String msgException = String.format(constantesExepciones.ERROR_TRANSICION_ASIGNAR_EMPLEADO, incidencia.getEstado().getNombreEstado());
        throw new Exception(msgException);
    }
    default void resolverIncidencia(Incidencia incidencia) throws Exception{
        String msgException = String.format(constantesExepciones.ERROR_TRANSICION_ASIGNAR_EMPLEADO, incidencia.getEstado().getNombreEstado());
        throw new Exception(msgException);
    }
}