package org.utn.dominio.estado;

import org.utn.dominio.incidente.Incidencia;

public class Confirmado implements Estado{
    @Override
    public void asignarEmpleado(Incidencia incidencia) {
        // No hace nada, no es una transición válida
        //Comentario Test
    }

    @Override
    public void confirmarIncidencia(Incidencia incidencia) {
        // No hace nada, no es una transición válida
    }

    @Override
    public void desestimarIncidencia(Incidencia incidencia) {
        incidencia.setEstado(new Desestimado());
    }

    @Override
    public void iniciarProgreso(Incidencia incidencia) {
        incidencia.setEstado(new EnProgreso());
    }

    @Override
    public void resolverIncidencia(Incidencia incidencia) {
        // No hace nada, no es una transición válida
    }
}
