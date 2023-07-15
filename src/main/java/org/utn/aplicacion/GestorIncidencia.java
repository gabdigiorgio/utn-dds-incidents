package org.utn.aplicacion;

import org.jetbrains.annotations.NotNull;
import org.utn.dominio.incidencia.CodigoCatalogo;
import org.utn.dominio.incidencia.EnumEstado;
import org.utn.dominio.incidencia.Incidencia;
import org.utn.dominio.incidencia.factory.IncidenciaFactory;
import org.utn.persistencia.RepoIncidencias;
import org.utn.presentacion.api.inputs.ChangeState;
import org.utn.presentacion.api.inputs.CreateIncident;
import org.utn.presentacion.api.inputs.EditIncident;
import org.utn.utils.DateUtils;
import org.utn.utils.exceptions.validador.FormatoCodigoCatalogoInvalidoException;

import java.time.LocalDate;
import java.util.List;

public class GestorIncidencia {

    private final RepoIncidencias repoIncidencias;

    public GestorIncidencia(RepoIncidencias repoIncidencias) {
        this.repoIncidencias = repoIncidencias;
    }

    public List<Incidencia> getIncidents(
        Integer limit, 
        String orderBy,
        String status, 
        String place
    ) throws FormatoCodigoCatalogoInvalidoException {
        List<Incidencia> incidents;

        incidents = repoIncidencias.findIncidents(limit, status, orderBy, place);
        return incidents;
    }

    public Incidencia createIncident(CreateIncident data) throws FormatoCodigoCatalogoInvalidoException {
        Incidencia nuevaIncidencia = nuevaIncidencia(new CodigoCatalogo(data.code),
                DateUtils.parsearFecha(data.reportDate),
                data.description,
                EnumEstado.REPORTADO.getNombreEstado(),
                null,
                data.reporterId,
                null,
                null);
        repoIncidencias.save(nuevaIncidencia);
        return nuevaIncidencia;
    }

    public Incidencia editIncident(Integer id, EditIncident data) throws Exception {
        Incidencia incident = repoIncidencias.getById(id);
        if (incident == null) throw new Exception("INCIDENT_NOT_FOUND");

        // data.status,
        if (data.employeeId != null) incident.setEmpleado(data.employeeId);
        if (data.closedDate != null) incident.setClosedDate(DateUtils.parsearFecha(data.closedDate));
        if (data.rejectedReason != null) incident.setRejectedReason(data.rejectedReason);

        // repoIncidencias
        repoIncidencias.update(incident);
        return incident;
    }

    public Incidencia updateIncidentState(Integer id, ChangeState request) throws Exception {
        Incidencia incident = repoIncidencias.getById(id);
        if (incident == null) throw new Exception("INCIDENT_NOT_FOUND");
        String estadoFormateado = request.estado.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
        EnumEstado siguienteEstado = EnumEstado.valueOf(estadoFormateado);
        incident.actualizarEstado(siguienteEstado, request.empleado, request.motivoRechazo);
        repoIncidencias.update(incident);
        return incident;
    }

    public void deleteIncident(Integer id) throws Exception {
        Incidencia incident = repoIncidencias.getById(id);
        if (incident == null) throw new Exception("INCIDENT_NOT_FOUND");
        repoIncidencias.remove(id);
    }

    public Incidencia crearIncidencia(
        String codigoCatalogo,
        LocalDate fechaReporte,
        String descripcion,
        String estado,
        String operador,
        String personaReporto,
        LocalDate fechaCierre,
        String motivoRechazo
    ) throws FormatoCodigoCatalogoInvalidoException {
        Incidencia nuevaIncidencia = nuevaIncidencia(
            new CodigoCatalogo(codigoCatalogo),
            fechaReporte,
            descripcion,
            estado,
            operador,
            personaReporto,
            fechaCierre,
            motivoRechazo
        );
        repoIncidencias.save(nuevaIncidencia);
        return nuevaIncidencia;
    }

    @NotNull
    private static Incidencia nuevaIncidencia(CodigoCatalogo codigoCatalogo,
                                              LocalDate fechaReporte,
                                              String descripcion,
                                              String estado,
                                              String operador,
                                              String personaReporto,
                                              LocalDate fechaCierre,
                                              String motivoRechazo) {
        Incidencia nuevaIncidencia;
        switch (estado) {
            case "Reportado":
                nuevaIncidencia = IncidenciaFactory.crearIncidenciaReportado(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto);
                break;
            case "Asignado":
                nuevaIncidencia = IncidenciaFactory.crearIncidenciaAsignado(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre, motivoRechazo);
                break;
            case "Confirmado":
                nuevaIncidencia = IncidenciaFactory.crearIncidenciaConfirmado(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre);
                break;
            case "Desestimado":
                nuevaIncidencia = IncidenciaFactory.crearIncidenciaDesestimado(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre, motivoRechazo);
                break;
            case "En progreso":
                nuevaIncidencia = IncidenciaFactory.crearIncidenciaEnProgreso(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre, motivoRechazo);
                break;
            case "Solucionado":
                nuevaIncidencia = IncidenciaFactory.crearIncidenciaSolucionado(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre, motivoRechazo);
                break;
            default:
                throw new IllegalArgumentException("Estado de incidencia inválido: " + estado);
        }
        return nuevaIncidencia;
    }

}

