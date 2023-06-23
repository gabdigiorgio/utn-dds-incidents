package org.utn.dominio.estado;

import org.utn.dominio.incidencia.Incident;

public class Assigned implements Status {

    public String getStatusName(){
        return "Asignado";
    }

    public void confirmIncident(Incident incident) {
       /* if(incidencia.getCreador().notequals(incidencia.getEmpleado()))
                incidencia.setEstado(new Confirmado());*/
        //ver como se valida en caso de que no sea el creador el empleado
        incident.setStatus(new Confirmed());
    }

    public void rejectIncident(Incident incident) {
        incident.setStatus(new Rejected());
    }

   /* public void iniciarProgreso(Incidencia incidencia) {
        incidencia.setEstado(new EnProgreso());
    }*/

}
