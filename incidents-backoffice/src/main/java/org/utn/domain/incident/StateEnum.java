package org.utn.domain.incident;


public enum StateEnum implements State {
    REPORTED {
        @Override
        public String getStateName() {
            return "Reportado";
        }

        public void assignEmployee(Incident incident) {
            //Validar como dejar seteado quien es el empleado asignado ej:
            // incidencia.setEstado(new Asignado(empleado));
            //Si el creador es un empelado deberia pasar a automaticamente a confirmado
            incident.setState(ASSIGNED);
            //evitar que otro empleado pueda leer el incidente que no corresponde
        }

        public void dismissIncident(Incident incident) {
            incident.setState(DISMISSED);
        }
    },
    ASSIGNED {
        public String getStateName() {
            return "Asignado";
        }

        public void confirmIncident(Incident incident) {
       /* if(incidencia.getCreador().notequals(incidencia.getEmpleado()))
                incidencia.setEstado(EnumEstado.CONFIRMADO);*/
            //ver como se valida en caso de que no sea el creador el empleado
            incident.setState(CONFIRMED);
        }

        public void dismissIncident(Incident incident) {
            incident.setState(DISMISSED);
        }
    },
    CONFIRMED {
        public String getStateName(){
            return "Confirmado";
        }

        public void startProgress(Incident incident) {
            incident.setState(IN_PROGRESS);
        }
    },
    DISMISSED {
        public String getStateName(){
            return "Desestimado";
        }

    },
    IN_PROGRESS {
        public String getStateName(){
            return "En progreso";
        }

        public void resolveIncident(Incident incident) {
            incident.setState(RESOLVED);
        }
    },
    RESOLVED {
        public String getStateName(){
            return "Solucionado";
        }
    }
}
