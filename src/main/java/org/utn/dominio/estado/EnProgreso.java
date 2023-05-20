package org.utn.dominio.estado;

import org.utn.dominio.incidencia.Incidencia;

public class EnProgreso implements Estado {
    public String getNombreEstado(){
        return "En progreso";
    }

    public void resolverIncidencia(Incidencia incidencia) {
        incidencia.setEstado(new Solucionado());
    }
}