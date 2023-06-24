package org.utn.dominio.estado;

import org.utn.dominio.incidencia.Incident;

public class Confirmed implements Status {
    public String getStatusName(){
        return "Confirmado";
    }

    public void startProgress(Incident incident) {
        incident.setStatus(new InProgress());
    }

}
