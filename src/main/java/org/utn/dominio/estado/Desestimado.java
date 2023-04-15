package org.utn.dominio.estado;

import org.utn.dominio.incidente.Incidencia;

public class Desestimado implements Estado {
    public String getNombreEstado(){
        return "Desestimado";
    }
}
