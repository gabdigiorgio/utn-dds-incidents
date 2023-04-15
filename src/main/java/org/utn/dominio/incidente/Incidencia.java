package org.utn.dominio.incidente;

import org.utn.dominio.estado.Estado;

public class Incidencia {
    private String codigoCatalogo;
    private String fechaReporte;
    private String descripcion;
    private String operador;
    private String reportadoPor;
    private String fechaCierre;
    private String motivoRechazo;
    private Estado estado;
    private String empleado;

    public Incidencia(String codigoCatalogo, String fechaReporte, String descripcion, String operador, String reportadoPor, String fechaCierre, String motivoRechazo, Estado estado) {
        this.codigoCatalogo = codigoCatalogo;
        this.fechaReporte = fechaReporte;
        this.descripcion = descripcion;
        this.operador = operador;
        this.reportadoPor = reportadoPor;
        this.fechaCierre = fechaCierre;
        this.motivoRechazo = motivoRechazo;
        this.estado = estado;
    }

    public String getCodigoCatalogo() {
        return codigoCatalogo;
    }

    public String getFechaReporte() {
        return fechaReporte;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getOperador() {
        return operador;
    }

    public String getReportadoPor() {
        return reportadoPor;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public Estado getEstado() {
        return estado;
    }

    public String getCreador(){
        return reportadoPor;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    /******   Inicio metodos que impactan a estados   ******/
    public void asignarEmpleado(String empleado) throws Exception {
        this.estado.asignarEmpleado(this);
        this.setEmpleado(empleado);
    }

    public void confirmarIncidencia() throws Exception {
        this.estado.confirmarIncidencia(this);
    }

    public void desestimarIncidencia() throws Exception {
        this.estado.desestimarIncidencia(this);
    }

    public void iniciarProgreso() throws Exception {
        this.estado.iniciarProgreso(this);
    }

    public void resolverIncidencia() throws Exception {
        this.estado.resolverIncidencia(this);
    }


    /******   Fin metodos que impactan a estados   ******/
//////////////////////////////////////////
    public String getEmpleado() { return empleado;}

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }


}