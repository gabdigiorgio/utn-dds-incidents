import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.utn.dominio.estado.*;
import org.utn.dominio.incidente.Incidencia;

import static org.junit.Assert.*;

public class TestIncidente {
    protected Incidencia incidencia;
    protected Estado estado;

    private void initializeIncidencia(){
        this.estado=new Reportado();
        this.incidencia= new Incidencia("1234-56","15042023","","Operador","reportador","29052023","",estado);
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
        String empleado="Jorge";

        incidencia.asignarEmpleado(empleado);

        assertTrue(incidencia.getEstado() instanceof Asignado);
        assertEquals(incidencia.getEmpleado(),empleado);
    }
    @Test //despues probar por validacion y por creador=empleado
    public void deAsignadoAConfirmado() throws Exception {
        String empleado="Jorge";

        incidencia.asignarEmpleado(empleado);
        incidencia.confirmarIncidencia();

        assertTrue(incidencia.getEstado() instanceof Confirmado);
    }

    @Test
    public void deAsignadoADesestimado() throws Exception {
        String empleado="Jorge";

        incidencia.asignarEmpleado(empleado);
        incidencia.desestimarIncidencia();

        //hay que revisar por que se desestima
        assertTrue(incidencia.getEstado() instanceof Desestimado);
    }
    //casos no felices

    @Test
    public void testAsignarEmpleadoException() throws Exception {
        String empleado="Jorge";
        incidencia.asignarEmpleado(empleado);

        try {
            incidencia.asignarEmpleado(empleado);
            fail("Debería lanzar una excepción al intentar asignar un empleado dos veces");
        } catch (Exception e) {
            assertEquals("No es posible asignar empleado con la incidencia en estado: Asignado", e.getMessage());
        }
    }
}
