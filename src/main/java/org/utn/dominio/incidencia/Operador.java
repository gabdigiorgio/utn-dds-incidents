package org.utn.dominio.incidencia;

public class Operador {
    private String nombre;
    //Servicios

    public Operador(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
