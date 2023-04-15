package org.utn.estado;

import org.utn.incidente.Incidencia;

public interface Estado {

    void asignarEmpleado(Incidencia incidencia, String empleado);

    void confirmarIncidencia(Incidencia incidencia);
    void desestimarIncidencia(Incidencia incidencia);
    void iniciarProgreso(Incidencia incidencia);
    void resolverIncidencia(Incidencia incidencia);
}