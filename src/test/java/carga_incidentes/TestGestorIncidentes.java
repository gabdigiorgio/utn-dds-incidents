package carga_incidentes;

import org.junit.Test;
import org.utn.persistencia.MemRepoIncidencias;
import org.utn.utils.exceptions.validador.DatosIncompletosException;
import org.utn.aplicacion.GestorIncidencia;

public class TestGestorIncidentes {


    @Test(expected = DatosIncompletosException.class)
    public void testIncidenciaDatosIncompletosExeption() throws Exception {
        GestorIncidencia gestor = new GestorIncidencia(MemRepoIncidencias.obtenerInstancia());
        gestor.crearIncidencia("","","","Invalido","","","","");
    }
}
