import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.utn.dominio.estado.*;
import org.utn.dominio.excepciones.constantesExepciones;
import org.utn.dominio.incidente.*;
import org.utn.infraestructura.persistencia.MemRepoIncidencias;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestIncidente {
    private Incidencia incidencia1;
    private Incidencia incidencia2;
    private Incidencia incidencia3;
    private Incidencia incidencia4;

    private final MemRepoIncidencias repoIncidencias= MemRepoIncidencias.obtenerInstancia();
    private final String empleado="Jorge";

    private void initializeIncidencia(){
        Estado estadoReportado = new Reportado();
        Estado estadoAsignado = new Asignado();
        this.incidencia1 = new Incidencia("1234-56","15042023","","Operador1","reportador1","29052023","", estadoReportado);
        this.incidencia2 = new Incidencia("1533-24","13042023","","Operador2","reportador2","29042023","", estadoAsignado);
        this.incidencia3 = new Incidencia("7543-21","19042023","","Operador3","reportador3","29062023","", estadoAsignado);
        this.incidencia4 = new Incidencia("1533-24","10042023","","Operador4","reportador4","29022023","", estadoAsignado);

        repoIncidencias.save(incidencia1);
        repoIncidencias.save(incidencia2);
        repoIncidencias.save(incidencia3);
        repoIncidencias.save(incidencia4);


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
    public void testDesestimarIncidenciaSolucionadaException() {
        String msgException = String.format(constantesExepciones.ERROR_TRANSICION_DESESTIMAR_INCIDENCIA, "Solucionado");
        Incidencia incidenciaSoluionada = new Incidencia("1234-56","15042023","","Operador1","reportador1","29052023","", new Solucionado());

        try {
            incidenciaSoluionada.desestimarIncidencia();
            fail("Debería lanzar una excepción al intentar desestimar una incidencia solucionada");
        } catch (Exception e) {
            assertEquals(msgException, e.getMessage());
        }
    }

    @Test
    public void testTransicionReportadoExceptionsRefactor() {
        String[][] testData = {
                {"Confirmar", constantesExepciones.ERROR_TRANSICION_CONFIRMAR_INCIDENCIA},
                {"IniciarProgreso", constantesExepciones.ERROR_TRANSICION_INICIAR_PROGRESO},
                {"Resolver", constantesExepciones.ERROR_TRANSICION_RESOLVER_INCIDENCIA}
        };
        Incidencia incidenciaReportada = new Incidencia("1234-56", "15042023", "", "Operador1", "reportador1", "29052023", "", new Reportado());

        for (String[] data : testData) {
            String transicion = data[0];
            String msgException = String.format(data[1], "Reportado");

            try{
                switch (transicion) {
                    case "Confirmar"       -> incidenciaReportada.confirmarIncidencia();
                    case "IniciarProgreso" -> incidenciaReportada.iniciarProgreso();
                    case "Resolver"        -> incidenciaReportada.resolverIncidencia();
                }
            }
            catch (Exception e){
                assertEquals(msgException, e.getMessage());
            }
        }
    }

    @Test
    public void testTransicionAsignadoExceptions() {
        String[][] testData = {
                {"Asignar", constantesExepciones.ERROR_TRANSICION_ASIGNAR_EMPLEADO},
                {"IniciarProgreso", constantesExepciones.ERROR_TRANSICION_INICIAR_PROGRESO}
                //{"Resolver", constantesExepciones.ERROR_TRANSICION_RESOLVER_INCIDENCIA}
        };
        Incidencia incidenciaReportada = new Incidencia("1234-56", "15042023", "", "Operador1", "reportador1", "29052023", "", new Asignado());

        for (String[] data : testData) {
            String transicion = data[0];
            String msgException = String.format(data[1], "Asignado");

            try{
                switch (transicion) {
                    case "Asignar"       -> incidenciaReportada.asignarEmpleado("");
                    case "IniciarProgreso" -> incidenciaReportada.iniciarProgreso();
                    case "Resolver"        -> incidenciaReportada.resolverIncidencia();
                }
            }
            catch (Exception e){
                assertEquals(msgException, e.getMessage());
            }
        }
    }

    @Test
    public void testTransicionConfirmadoExceptions() {
        String[][] testData = {
                {"Asignar", constantesExepciones.ERROR_TRANSICION_ASIGNAR_EMPLEADO},
                {"Confirmar", constantesExepciones.ERROR_TRANSICION_CONFIRMAR_INCIDENCIA},
                {"Desestimar", constantesExepciones.ERROR_TRANSICION_DESESTIMAR_INCIDENCIA},
                {"Resolver", constantesExepciones.ERROR_TRANSICION_RESOLVER_INCIDENCIA}
        };
        Incidencia incidenciaReportada = new Incidencia("1234-56", "15042023", "", "Operador1", "reportador1", "29052023", "", new Confirmado());

        for (String[] data : testData) {
            String transicion = data[0];
            String msgException = String.format(data[1], "Confirmado");

            try{
                switch (transicion) {
                    case "Asignar"       -> incidenciaReportada.asignarEmpleado("");
                    case "Confirmar"       -> incidenciaReportada.confirmarIncidencia();
                    case "Desestimar"       -> incidenciaReportada.desestimarIncidencia();
                    case "Resolver"        -> incidenciaReportada.resolverIncidencia();
                }
            }
            catch (Exception e){
                assertEquals(msgException, e.getMessage());
            }
        }
    }

    @Test
    public void testTransicionDesestimadoExceptions() {
        String[][] testData = {
                {"Asignar", constantesExepciones.ERROR_TRANSICION_ASIGNAR_EMPLEADO},
                {"Confirmar", constantesExepciones.ERROR_TRANSICION_CONFIRMAR_INCIDENCIA},
                {"Desestimar", constantesExepciones.ERROR_TRANSICION_DESESTIMAR_INCIDENCIA},
                {"IniciarProgreso", constantesExepciones.ERROR_TRANSICION_INICIAR_PROGRESO},
                {"Resolver", constantesExepciones.ERROR_TRANSICION_RESOLVER_INCIDENCIA}
        };
        Incidencia incidenciaReportada = new Incidencia("1234-56", "15042023", "", "Operador1", "reportador1", "29052023", "", new Desestimado());

        for (String[] data : testData) {
            String transicion = data[0];
            String msgException = String.format(data[1], "Desestimado");

            try{
                switch (transicion) {
                    case "Asignar"       -> incidenciaReportada.asignarEmpleado("");
                    case "Confirmar"       -> incidenciaReportada.confirmarIncidencia();
                    case "Desestimar"       -> incidenciaReportada.desestimarIncidencia();
                    case "IniciarProgreso" -> incidenciaReportada.iniciarProgreso();
                    case "Resolver"        -> incidenciaReportada.resolverIncidencia();
                }
            }
            catch (Exception e){
                assertEquals(msgException, e.getMessage());
            }
        }
    }

    @Test
    public void testTransicionEnProgresoExceptions() {
        String[][] testData = {
                {"Asignar", constantesExepciones.ERROR_TRANSICION_ASIGNAR_EMPLEADO},
                {"Confirmar", constantesExepciones.ERROR_TRANSICION_CONFIRMAR_INCIDENCIA},
                {"Desestimar", constantesExepciones.ERROR_TRANSICION_DESESTIMAR_INCIDENCIA},
                {"IniciarProgreso", constantesExepciones.ERROR_TRANSICION_INICIAR_PROGRESO},
        };
        Incidencia incidenciaReportada = new Incidencia("1234-56", "15042023", "", "Operador1", "reportador1", "29052023", "", new EnProgreso());

        for (String[] data : testData) {
            String transicion = data[0];
            String msgException = String.format(data[1], "En progreso");

            try{
                switch (transicion) {
                    case "Asignar"       -> incidenciaReportada.asignarEmpleado("");
                    case "Confirmar"       -> incidenciaReportada.confirmarIncidencia();
                    case "Desestimar"       -> incidenciaReportada.desestimarIncidencia();
                    case "IniciarProgreso" -> incidenciaReportada.iniciarProgreso();
                }
            }
            catch (Exception e){
                assertEquals(msgException, e.getMessage());
            }

        }
    }

    @Test
    public void testTransicionSolucionadoExceptions() {
        String[][] testData = {
                {"Asignar", constantesExepciones.ERROR_TRANSICION_ASIGNAR_EMPLEADO},
                {"Confirmar", constantesExepciones.ERROR_TRANSICION_CONFIRMAR_INCIDENCIA},
                {"Desestimar", constantesExepciones.ERROR_TRANSICION_DESESTIMAR_INCIDENCIA},
                {"IniciarProgreso", constantesExepciones.ERROR_TRANSICION_INICIAR_PROGRESO},
                {"Resolver", constantesExepciones.ERROR_TRANSICION_RESOLVER_INCIDENCIA}
        };
        Incidencia incidenciaReportada = new Incidencia("1234-56", "15042023", "", "Operador1", "reportador1", "29052023", "", new Solucionado());

        for (String[] data : testData) {
            String transicion = data[0];
            String msgException = String.format(data[1], "Solucionado");

            Exception e = assertThrows(Exception.class, () -> {
                switch (transicion) {
                    case "Asignar"       -> incidenciaReportada.asignarEmpleado("");
                    case "Confirmar"       -> incidenciaReportada.confirmarIncidencia();
                    case "Desestimar"       -> incidenciaReportada.desestimarIncidencia();
                    case "IniciarProgreso" -> incidenciaReportada.iniciarProgreso();
                    case "Resolver"        -> incidenciaReportada.resolverIncidencia();
                }
            });
            assertEquals(msgException, e.getMessage());
        }
    }

}
