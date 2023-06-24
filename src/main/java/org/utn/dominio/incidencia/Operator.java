package org.utn.dominio.incidencia;

public class Operator {
    private String operatorName;
    //Servicios

    public Operator(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}
