package org.utn.dominio.estado;

import org.utn.dominio.incidente.Incidencia;

public class Confirmado implements Estado{
    public String getNombreEstado(){
        return "Confirmado";
    }

    public void desestimarIncidencia(Incidencia incidencia) {
        incidencia.setEstado(new Desestimado());
    }

    public void iniciarProgreso(Incidencia incidencia) {
        incidencia.setEstado(new EnProgreso());
    }

}