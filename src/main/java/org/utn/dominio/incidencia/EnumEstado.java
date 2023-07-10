package org.utn.dominio.incidencia;


public enum EnumEstado implements Estado {
    REPORTADO {
        @Override
        public String getNombreEstado() {
            return "Reportado";
        }


        public void asignarEmpleado(Incidencia incidencia) {
            //Validar como dejar seteado quien es el empleado asignado ej:
            // incidencia.setEstado(new Asignado(empleado));
            //Si el creador es un empelado deberia pasar a automaticamente a confirmado
            incidencia.setEstado(ASIGNADO);
            //evitar que otro empleado pueda leer el incidente que no corresponde
        }

        public void desestimarIncidencia(Incidencia incidencia) {
            incidencia.setEstado(DESESTIMADO);
        }
    },
    ASIGNADO {
        public String getNombreEstado() {
            return "Asignado";
        }

        public void confirmarIncidencia(Incidencia incidencia) {
       /* if(incidencia.getCreador().notequals(incidencia.getEmpleado()))
                incidencia.setEstado(EnumEstado.CONFIRMADO);*/
            //ver como se valida en caso de que no sea el creador el empleado
            incidencia.setEstado(CONFIRMADO);
        }

        public void desestimarIncidencia(Incidencia incidencia) {
            incidencia.setEstado(DESESTIMADO);
        }
    },
    CONFIRMADO {
        public String getNombreEstado(){
            return "Confirmado";
        }

        public void iniciarProgreso(Incidencia incidencia) {
            incidencia.setEstado(EN_PROGRESO);
        }
    },
    DESESTIMADO{
        public String getNombreEstado(){
            return "Desestimado";
        }

    },
    EN_PROGRESO{
        public String getNombreEstado(){
            return "En progreso";
        }

        public void resolverIncidencia(Incidencia incidencia) {
            incidencia.setEstado(SOLUCIONADO);
        }
    },
    SOLUCIONADO {
        public String getNombreEstado(){
            return "Solucionado";
        }
    }
}
