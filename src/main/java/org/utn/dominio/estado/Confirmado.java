package org.utn.dominio.estado;

import org.utn.dominio.incidencia.Incidencia;

public class Confirmado implements Estado{
    public String getNombreEstado(){
        return "Confirmado";
    }

    public void iniciarProgreso(Incidencia incidencia) {
        incidencia.setEstado(new EnProgreso());
    }

}
