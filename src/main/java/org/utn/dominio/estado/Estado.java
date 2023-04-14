package org.utn.dominio.estado;

import org.utn.dominio.incidente.Incidencia;

public interface Estado {
    void asignarEmpleado(Incidencia incidencia);
    void confirmarIncidencia(Incidencia incidencia);
    void desestimarIncidencia(Incidencia incidencia);
    void iniciarProgreso(Incidencia incidencia);
    void resolverIncidencia(Incidencia incidencia);
}