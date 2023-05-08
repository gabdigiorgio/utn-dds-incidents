package carga_incidentes;

import org.junit.Test;
import org.utn.exceptions.validador.DatosIncompletosException;
import org.utn.exceptions.validador.EstadoInvalidoException;
import org.utn.presentacion.carga_incidentes.GestorIncidencia;

public class TestGestorIncidentes {


    @Test(expected = DatosIncompletosException.class)
    public void testIncidenciaDatosIncompletosExeption() throws Exception {
        GestorIncidencia gestor = new GestorIncidencia();
        gestor.procesarLinea("","","","Invalido","","","","");
    }
}
