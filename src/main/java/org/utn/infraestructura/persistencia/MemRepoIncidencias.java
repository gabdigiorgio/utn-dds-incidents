package org.utn.infraestructura.persistencia;

import org.utn.dominio.incidente.Incidencia;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class MemRepoIncidencias implements RepoIncidencias {
    private List<Incidencia> incidencias = new ArrayList<>();

    private static MemRepoIncidencias instanciaUnica;
    private MemRepoIncidencias() {}

    public static MemRepoIncidencias obtenerInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new MemRepoIncidencias();
        }
        return instanciaUnica;
    }

    public void save(Incidencia incidencia) {
        incidencias.add(incidencia);
    }

    public List<Incidencia> findByEstado(String estado) {
        return incidencias.stream().filter(i -> i.getNombreEstado().equals(estado)).collect(Collectors.toList());
    }

    public int count() {
        return incidencias.size();
    }
}
