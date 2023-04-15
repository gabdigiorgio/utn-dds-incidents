package org.utn.dominio.estado;

import org.utn.dominio.incidente.Incidencia;

public class EnProgreso implements Estado {
    public String getNombreEstado(){
        return "En progreso";
    }

    @Override
    public void resolverIncidencia(Incidencia incidencia) {
        incidencia.setEstado(new Solucionado());
    }
}