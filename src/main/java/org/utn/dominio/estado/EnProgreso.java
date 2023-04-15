package org.utn.dominio.estado;

import org.utn.dominio.incidente.Incidencia;

public class EnProgreso implements Estado {
    @Override
    public void asignarEmpleado(Incidencia incidencia,String empleado) {
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
        incidencia.setEstado(new Solucionado());
    }
}