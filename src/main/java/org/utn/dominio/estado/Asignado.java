package org.utn.dominio.estado;

import org.utn.dominio.incidente.Incidencia;

public class Asignado implements Estado{

    public String getNombreEstado(){
        return "Asignado";
    }

    public void confirmarIncidencia(Incidencia incidencia) {
       /* if(incidencia.getCreador().notequals(incidencia.getEmpleado()))
                incidencia.setEstado(new Confirmado());*/
        //ver como se valida en caso de que no sea el creador el empleado
        incidencia.setEstado(new Confirmado());
    }

    public void desestimarIncidencia(Incidencia incidencia) {
        incidencia.setEstado(new Desestimado());
    }

    public void iniciarProgreso(Incidencia incidencia) {
        incidencia.setEstado(new EnProgreso());
    }

}
