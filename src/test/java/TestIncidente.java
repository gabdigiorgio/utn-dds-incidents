import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.utn.dominio.estado.*;
import org.utn.dominio.incidente.*;
import org.utn.infraestructura.persistencia.MemRepoIncidencias;

import java.util.List;

import static org.junit.Assert.*;

public class TestIncidente {
    private Incidencia incidencia1;
    private Incidencia incidencia2;
    private Incidencia incidencia3;
    private Incidencia incidencia4;
    private Estado estadoReportado;
    private Estado estadoAsignado;

    private MemRepoIncidencias repoIncidencias= new MemRepoIncidencias();

    private String empleado="Jorge";

    private void initializeIncidencia(){
        this.estadoReportado =new Reportado();
        this.estadoAsignado = new Asignado();
        this.incidencia1 = new Incidencia("1234-56","15042023","","Operador1","reportador1","29052023","",estadoReportado);
        this.incidencia2 = new Incidencia("1533-24","17042023","","Operador2","reportador2","29052023","",estadoAsignado);
        this.incidencia3 = new Incidencia("7543-21","19042023","","Operador3","reportador3","29052023","",estadoAsignado);
        this.incidencia4 = new Incidencia("5723-97","10042023","","Operador4","reportador4","29052023","",estadoAsignado);
    }

    @Before
    public void initialize() {
        this.initializeIncidencia();
    }

    @After
    public void clean(){
    }

    /////*CASOS FELICES*///////
    @Test
    public void deReportadoAAsignado() throws Exception {

        incidencia1.asignarEmpleado(empleado);

        assertTrue(incidencia1.getEstado() instanceof Asignado);
        assertEquals(incidencia1.getEmpleado(),empleado);
    }
    @Test //despues probar por validacion y por creador=empleado
    public void deAsignadoAConfirmado() throws Exception {

        incidencia1.asignarEmpleado(empleado);
        incidencia1.confirmarIncidencia();

        assertTrue(incidencia1.getEstado() instanceof Confirmado);
    }

    @Test
    public void deAsignadoADesestimado() throws Exception {

        incidencia1.asignarEmpleado(empleado);
        incidencia1.desestimarIncidencia();

        //hay que revisar por que se desestima
        assertTrue(incidencia1.getEstado() instanceof Desestimado);
    }

    //casos no felices

    @Test
    public void testAsignarEmpleadoException() throws Exception {
        incidencia1.asignarEmpleado(empleado);

        try {
            incidencia1.asignarEmpleado(empleado);
            fail("Debería lanzar una excepción al intentar asignar un empleado dos veces");
        } catch (Exception e) {
            assertEquals("No es posible asignar empleado con la incidencia en estado: Asignado", e.getMessage());
        }
    }

    @Test
    public void funcionalidadDeRepoIncidencia(){
        repoIncidencias.save(incidencia1);
        repoIncidencias.save(incidencia2);
        repoIncidencias.save(incidencia3);
        repoIncidencias.save(incidencia4);

        assertEquals(repoIncidencias.findByEstado("Asignado").size(),3);
        assertEquals(repoIncidencias.findByEstado("Reportado").size(),1);
        assertEquals(repoIncidencias.count(), 4);
    }
}
