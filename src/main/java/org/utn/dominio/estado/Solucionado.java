package org.utn.dominio.estado;

import org.utn.dominio.incidente.Incidencia;

public class Solucionado implements Estado {
    public String getNombreEstado(){
        return "Solucionado";
    }
}