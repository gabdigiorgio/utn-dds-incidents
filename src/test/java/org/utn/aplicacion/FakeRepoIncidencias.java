package org.utn.aplicacion;

import org.utn.dominio.estado.Estado;
import org.utn.dominio.incidencia.CodigoCatalogo;
import org.utn.dominio.incidencia.Incidencia;
import org.utn.dominio.incidencia.RepoIncidencias;

import java.util.List;

public class FakeRepoIncidencias implements RepoIncidencias {

    private Incidencia incidenciaGuardada;

    @Override
    public void save(Incidencia incidencia) {
        incidenciaGuardada = incidencia;
    }

    @Override
    public void update(Incidencia incidencia) {
        incidenciaGuardada = incidencia;
    }

    @Override
    public void remove(Integer id) {
        return;
    }

    @Override
    public Incidencia getById(Integer id) {
        return null;
    }

    @Override
    public List<Incidencia> findByEstado(String estado) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public List<Incidencia> ordenarPorMasReciente() {
        return null;
    }

    @Override
    public List<Incidencia> ordenarPorLaMasVieja() {
        return null;
    }

    @Override
    public List<Incidencia> incidenciasDeUnLugar(String lugar) {
        return null;
    }

    @Override
    public List<Incidencia> obtenerIncidencias(int cantidad, String orden) {
        return null;
    }

    @Override
    public List<Incidencia> obtenerIncidencias(int cantidad, Estado estado) {
        return null;
    }

    @Override
    public List<Incidencia> obtenerIncidenciasByPlace(CodigoCatalogo codigoCatalogo) {
        return null;
    }

    @Override
    public List<Incidencia> obtenerIncidenciasByEstado(int cantidad, String estado) {
        return null;
    }

    public Incidencia getIncidenciaGuardada() {
        return incidenciaGuardada;
    }
}
