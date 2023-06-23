package org.utn.dominio.estado;

import org.utn.dominio.incidencia.Incident;

public class InProgress implements Status {
    public String getStatusName(){
        return "En progreso";
    }

    public void resolveIncident(Incident incident) {
        incident.setStatus(new Solved());
    }
}