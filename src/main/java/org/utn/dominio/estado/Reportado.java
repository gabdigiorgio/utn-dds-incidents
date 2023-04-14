package org.utn.dominio.estado;

import org.utn.dominio.incidente.Incidencia;

public class Reportado implements Estado {

    @Override
    public void asignarEmpleado(Incidencia incidencia) {
        //Validar como dejar seteado quien es el empleado asignado ej:
        // incidencia.setEstado(new Asignado(empleado));
        //Si el creador es un empelado deberia pasar a automaticamente a confirmado
        incidencia.setEstado(new Asignado());
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
        // No hace nada, no es una transición válida
    }

    @Override
    public void resolverIncidencia(Incidencia incidencia) {
        // No hace nada, no es una transición válida
    }
}
