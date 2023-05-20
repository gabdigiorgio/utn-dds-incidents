package org.utn.dominio.estado;

import org.utn.dominio.incidencia.Incidencia;

public class Reportado implements Estado {
    public String getNombreEstado(){
        return "Reportado";
    }

    public void asignarEmpleado(Incidencia incidencia) {
        //Validar como dejar seteado quien es el empleado asignado ej:
        // incidencia.setEstado(new Asignado(empleado));
        //Si el creador es un empelado deberia pasar a automaticamente a confirmado
        incidencia.setEstado(new Asignado());
        //evitar que otro empleado pueda leer el incidente que no corresponde
    }

    public void desestimarIncidencia(Incidencia incidencia) {
        incidencia.setEstado(new Desestimado());
    }
}
