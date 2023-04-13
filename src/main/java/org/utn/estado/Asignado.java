package org.utn.estado;

import org.utn.incidente.Incidencia;

public class Asignado implements Estado{
    @Override
    public void asignarEmpleado(Incidencia incidencia) {
        //Agregar exception Ya hay un empleado asinado
    }

    @Override
    public void confirmarIncidencia(Incidencia incidencia) {
        incidencia.setEstado(new Confirmado());
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
        // No hace nada, no se puede resolver directamente desde este estado
    }
}
