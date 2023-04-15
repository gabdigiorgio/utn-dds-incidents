package org.utn.dominio.estado;

import org.utn.dominio.incidente.Incidencia;

public class Asignado implements Estado{
    @Override
    public void asignarEmpleado(Incidencia incidencia) {
        //Agregar exception Ya hay un empleado asinado
    }

    @Override
    public void confirmarIncidencia(Incidencia incidencia) {
       /* if(incidencia.getCreador().notequals(incidencia.getEmpleado()))
                incidencia.setEstado(new Confirmado());*/
        //ver como se valida en caso de que no sea el creador el empleado
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
