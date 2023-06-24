package org.utn.dominio.estado;

import org.utn.dominio.incidencia.Incident;

public class Reported implements Status {
    public String getStatusName(){
        return "Reportado";
    }

    public void assignEmployee(Incident incident) {
        //Validar como dejar seteado quien es el empleado asignado ej:
        // incidencia.setEstado(new Asignado(empleado));
        //Si el creador es un empelado deberia pasar a automaticamente a confirmado
        incident.setStatus(new Assigned());
        //evitar que otro empleado pueda leer el incidente que no corresponde
    }

    public void rejectIncident(Incident incident) {
        incident.setStatus(new Rejected());
    }
}
