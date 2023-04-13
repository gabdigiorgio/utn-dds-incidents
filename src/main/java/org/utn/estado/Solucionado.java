package org.utn.estado;

import org.utn.incidente.Incidencia;

public class Solucionado implements Estado {

    @Override
    public void asignarEmpleado(Incidencia incidencia) {
        // No hace nada, no es una transición válida
    }

    @Override
    public void confirmarIncidencia(Incidencia incidencia) {
        // No hace nada, no es una transición válida
    }

    @Override
    public void desestimarIncidencia(Incidencia incidencia) {
        // No hace nada, no es una transición válida
    }

    @Override
    public void iniciarProgreso(Incidencia incidencia) {
        // No hace nada, no es una transición válida
    }

    @Override
    public void resolverIncidencia(Incidencia incidencia) {
        // No hace nada, no es una transición válida
    }
}