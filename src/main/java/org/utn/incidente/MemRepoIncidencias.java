package org.utn.incidente;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MemRepoIncidencias implements RepoIncidencias {
    private List<Incidencia> incidencias = new ArrayList<>();

    @Override
    public void save(Incidencia incidencia) {
        incidencias.add(incidencia);
    }

    @Override
    public List<Incidencia> findByEstado(String estado) {
        return incidencias.stream().filter(i -> Objects.equals(i.getEstado().toString(), estado)).collect(Collectors.toList());
    }

    @Override
    public int count() {
        return incidencias.size();
    }
}
