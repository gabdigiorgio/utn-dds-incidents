package org.utn.dominio.incidencia;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.utn.dominio.estado.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Incidencia {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //#TODO
    private Integer id;
    @Transient
    public CodigoCatalogo codigoCatalogo;
    public LocalDate fechaReporte;
    public String descripcion;
    public String operador;
    public String reportadoPor; // Posiblemente en un futuro sea una Clase (Pagina 7)
    public LocalDate fechaCierre;
    public String motivoRechazo;
    @Convert(converter = EstadoConverter.class)
    public Estado estado;
    public String empleado; // Posiblemente en un futuro sea una Clase (Pagina 8)


    public Incidencia(
        CodigoCatalogo codigoCatalogo,
        LocalDate fechaReporte,
        String descripcion,
        String operador,
        String reportadoPor,
        LocalDate fechaCierre,
        String motivoRechazo,
        Estado estado
    ) {
        this.codigoCatalogo = codigoCatalogo;
        this.fechaReporte = fechaReporte;
        this.descripcion = descripcion;
        this.operador = operador;
        this.reportadoPor = reportadoPor;
        this.fechaCierre = fechaCierre;
        if (motivoRechazo.isEmpty()) this.motivoRechazo = "";
        else this.motivoRechazo = motivoRechazo;
        this.estado = estado;
    }

    protected Incidencia() {
        super();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public CodigoCatalogo getCodigoCatalogo() {
        return codigoCatalogo;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate getFechaReporte() {
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate getFechaCierre() {
        return fechaCierre;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public Estado getEstado() {
        return estado;
    }

    public String getNombreEstado(){return estado.getNombreEstado();}

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

    public void desestimarIncidencia(String motivoRechazo) throws Exception {
        this.estado.desestimarIncidencia(this);
        this.setRejectedReason(motivoRechazo);
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

    public void setClosedDate(LocalDate date) {
        this.fechaCierre = date;
    }

    public void setRejectedReason(String rejectedReason) {
        this.motivoRechazo = rejectedReason;
    }


}