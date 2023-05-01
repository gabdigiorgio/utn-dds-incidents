package carga_incidentes;

import org.junit.Test;
import org.utn.exceptions.validador.EstadoInvalidoException;
import org.utn.presentacion.carga_incidentes.GestorIncidencias;

public class TestGestorIncidentes {


    @Test(expected = EstadoInvalidoException.class)
    public void testIncidenciaEstadoInvalido() throws Exception {
        String[] datos ={"","","","Invalido","","","",""};
        GestorIncidencias gestor = new GestorIncidencias();
        gestor.procesarLinea(datos);
    }
}
