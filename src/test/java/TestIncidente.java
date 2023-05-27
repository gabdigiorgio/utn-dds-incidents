import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.utn.dominio.estado.*;
import org.utn.utils.constantesExepciones;
import org.utn.dominio.incidencia.*;
import org.utn.persistencia.MemRepoIncidencias;
import org.utn.utils.exceptions.validador.DatosIncompletosException;
import org.utn.utils.exceptions.validador.FormatoCodigoCatalogInvalidoException;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class TestIncidente {
    private Incidencia incidencia1;
    private Incidencia incidencia2;
    private Incidencia incidencia3;
    private Incidencia incidencia4;

    private final MemRepoIncidencias repoIncidencias = MemRepoIncidencias.obtenerInstancia();
    private final String empleado = "Jorge";

    private void initializeIncidencia() throws FormatoCodigoCatalogInvalidoException {
        Estado estadoReportado = new Reportado();
        Estado estadoAsignado = new Asignado();
        this.incidencia1 = new Incidencia(new CodigoCatalogo("1234-56"),
                LocalDate.of(2023, 4, 15),
                "",
                "Operador1",
                "reportador1",
                LocalDate.of(2023, 5, 29),
                "",
                estadoReportado);
        this.incidencia2 = new Incidencia(new CodigoCatalogo("1533-24"),
                LocalDate.of(2023, 4, 13),
                "",
                "Operador2",
                "reportador2",
                LocalDate.of(2023, 4, 29),
                "",
                estadoAsignado);
        this.incidencia3 = new Incidencia(new CodigoCatalogo("7543-21"),
                LocalDate.of(2023, 4, 19),
                "",
                "Operador3",
                "reportador3",
                LocalDate.of(2023, 6, 29),
                "",
                estadoAsignado);
        this.incidencia4 = new Incidencia(new CodigoCatalogo("1533-24"),
                LocalDate.of(2023, 4, 10),
                "",
                "Operador4",
                "reportador4",
                LocalDate.of(2023, 2, 28),
                "",
                estadoAsignado);

        repoIncidencias.save(incidencia1);
        repoIncidencias.save(incidencia2);
        repoIncidencias.save(incidencia3);
        repoIncidencias.save(incidencia4);
    }

    @Before
    public void initialize() throws DatosIncompletosException, FormatoCodigoCatalogInvalidoException {
        this.initializeIncidencia();
    }

    @After
    public void clean() {
    }

    /////*CASOS FELICES*///////
    @Test
    public void deReportadoAAsignado() throws Exception {

        incidencia1.asignarEmpleado(empleado);

        assertTrue(incidencia1.getEstado() instanceof Asignado);
        assertEquals(incidencia1.getEmpleado(), empleado);
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
    public void testDesestimarIncidenciaSolucionadaException() throws DatosIncompletosException, FormatoCodigoCatalogInvalidoException {
        String msgException = String.format(constantesExepciones.ERROR_TRANSICION_DESESTIMAR_INCIDENCIA, "Solucionado");
        Incidencia incidenciaSoluionada = new Incidencia(new CodigoCatalogo("1234-56"),
                LocalDate.of(2023, 4,15),
                "",
                "Operador1",
                "reportador1",
                LocalDate.of(2023, 5, 29),
                "",
                new Solucionado());

        try {
            incidenciaSoluionada.desestimarIncidencia();
            fail("Debería lanzar una excepción al intentar desestimar una incidencia solucionada");
        } catch (Exception e) {
            assertEquals(msgException, e.getMessage());
        }
    }

    @Test
    public void testTransicionReportadoExceptionsRefactor() throws DatosIncompletosException, FormatoCodigoCatalogInvalidoException {
        String[][] testData = {
                {"Confirmar", constantesExepciones.ERROR_TRANSICION_CONFIRMAR_INCIDENCIA},
                {"IniciarProgreso", constantesExepciones.ERROR_TRANSICION_INICIAR_PROGRESO},
                {"Resolver", constantesExepciones.ERROR_TRANSICION_RESOLVER_INCIDENCIA}
        };
        Incidencia incidenciaReportada = new Incidencia(new CodigoCatalogo("1234-56"),
                LocalDate.of(2023, 4, 15),
                "",
                "Operador1",
                "reportador1",
                LocalDate.of(2023, 5, 29),
                "",
                new Reportado());

        for (String[] data : testData) {
            String transicion = data[0];
            String msgException = String.format(data[1], "Reportado");

            try {
                switch (transicion) {
                    case "Confirmar" -> incidenciaReportada.confirmarIncidencia();
                    case "IniciarProgreso" -> incidenciaReportada.iniciarProgreso();
                    case "Resolver" -> incidenciaReportada.resolverIncidencia();
                }
            } catch (Exception e) {
                assertEquals(msgException, e.getMessage());
            }
        }
    }

    @Test
    public void testTransicionAsignadoExceptions() throws DatosIncompletosException, FormatoCodigoCatalogInvalidoException {
        String[][] testData = {
                {"Asignar", constantesExepciones.ERROR_TRANSICION_ASIGNAR_EMPLEADO},
                {"IniciarProgreso", constantesExepciones.ERROR_TRANSICION_INICIAR_PROGRESO}
                //{"Resolver", constantesExepciones.ERROR_TRANSICION_RESOLVER_INCIDENCIA}
        };
        Incidencia incidenciaReportada = new Incidencia(new CodigoCatalogo("1234-56"),
                LocalDate.of(2023, 4, 15),
                "",
                "Operador1",
                "reportador1",
                LocalDate.of(2023, 5, 29),
                "",
                new Asignado());

        for (String[] data : testData) {
            String transicion = data[0];
            String msgException = String.format(data[1], "Asignado");

            try {
                switch (transicion) {
                    case "Asignar" -> incidenciaReportada.asignarEmpleado("");
                    case "IniciarProgreso" -> incidenciaReportada.iniciarProgreso();
                    case "Resolver" -> incidenciaReportada.resolverIncidencia();
                }
            } catch (Exception e) {
                assertEquals(msgException, e.getMessage());
            }
        }
    }

    @Test
    public void testTransicionConfirmadoExceptions() throws DatosIncompletosException, FormatoCodigoCatalogInvalidoException {
        String[][] testData = {
                {"Asignar", constantesExepciones.ERROR_TRANSICION_ASIGNAR_EMPLEADO},
                {"Confirmar", constantesExepciones.ERROR_TRANSICION_CONFIRMAR_INCIDENCIA},
                {"Desestimar", constantesExepciones.ERROR_TRANSICION_DESESTIMAR_INCIDENCIA},
                {"Resolver", constantesExepciones.ERROR_TRANSICION_RESOLVER_INCIDENCIA}
        };
        Incidencia incidenciaReportada = new Incidencia(new CodigoCatalogo("1234-56"),
                LocalDate.of(2023, 4, 15),
                "",
                "Operador1",
                "reportador1",
                LocalDate.of(2023, 5, 29),
                "",
                new Confirmado());

        for (String[] data : testData) {
            String transicion = data[0];
            String msgException = String.format(data[1], "Confirmado");

            try {
                switch (transicion) {
                    case "Asignar" -> incidenciaReportada.asignarEmpleado("");
                    case "Confirmar" -> incidenciaReportada.confirmarIncidencia();
                    case "Desestimar" -> incidenciaReportada.desestimarIncidencia();
                    case "Resolver" -> incidenciaReportada.resolverIncidencia();
                }
            } catch (Exception e) {
                assertEquals(msgException, e.getMessage());
            }
        }
    }

    @Test
    public void testTransicionDesestimadoExceptions() throws DatosIncompletosException, FormatoCodigoCatalogInvalidoException {
        String[][] testData = {
                {"Asignar", constantesExepciones.ERROR_TRANSICION_ASIGNAR_EMPLEADO},
                {"Confirmar", constantesExepciones.ERROR_TRANSICION_CONFIRMAR_INCIDENCIA},
                {"Desestimar", constantesExepciones.ERROR_TRANSICION_DESESTIMAR_INCIDENCIA},
                {"IniciarProgreso", constantesExepciones.ERROR_TRANSICION_INICIAR_PROGRESO},
                {"Resolver", constantesExepciones.ERROR_TRANSICION_RESOLVER_INCIDENCIA}
        };
        Incidencia incidenciaReportada = new Incidencia(new CodigoCatalogo("1234-56"),
                LocalDate.of(2023, 4, 15),
                "",
                "Operador1",
                "reportador1",
                LocalDate.of(2023, 5, 29),
                "",
                new Desestimado());

        for (String[] data : testData) {
            String transicion = data[0];
            String msgException = String.format(data[1], "Desestimado");

            try {
                switch (transicion) {
                    case "Asignar" -> incidenciaReportada.asignarEmpleado("");
                    case "Confirmar" -> incidenciaReportada.confirmarIncidencia();
                    case "Desestimar" -> incidenciaReportada.desestimarIncidencia();
                    case "IniciarProgreso" -> incidenciaReportada.iniciarProgreso();
                    case "Resolver" -> incidenciaReportada.resolverIncidencia();
                }
            } catch (Exception e) {
                assertEquals(msgException, e.getMessage());
            }
        }
    }

    @Test
    public void testTransicionEnProgresoExceptions() throws DatosIncompletosException, FormatoCodigoCatalogInvalidoException {
        String[][] testData = {
                {"Asignar", constantesExepciones.ERROR_TRANSICION_ASIGNAR_EMPLEADO},
                {"Confirmar", constantesExepciones.ERROR_TRANSICION_CONFIRMAR_INCIDENCIA},
                {"Desestimar", constantesExepciones.ERROR_TRANSICION_DESESTIMAR_INCIDENCIA},
                {"IniciarProgreso", constantesExepciones.ERROR_TRANSICION_INICIAR_PROGRESO},
        };
        Incidencia incidenciaReportada = new Incidencia(new CodigoCatalogo("1234-56"),
                LocalDate.of(2023, 4, 15),
                "",
                "Operador1",
                "reportador1",
                LocalDate.of(2023, 5, 29),
                "",
                new EnProgreso());

        for (String[] data : testData) {
            String transicion = data[0];
            String msgException = String.format(data[1], "En progreso");

            try {
                switch (transicion) {
                    case "Asignar" -> incidenciaReportada.asignarEmpleado("");
                    case "Confirmar" -> incidenciaReportada.confirmarIncidencia();
                    case "Desestimar" -> incidenciaReportada.desestimarIncidencia();
                    case "IniciarProgreso" -> incidenciaReportada.iniciarProgreso();
                }
            } catch (Exception e) {
                assertEquals(msgException, e.getMessage());
            }

        }
    }

    @Test
    public void testTransicionSolucionadoExceptions() throws DatosIncompletosException, FormatoCodigoCatalogInvalidoException {
        String[][] testData = {
                {"Asignar", constantesExepciones.ERROR_TRANSICION_ASIGNAR_EMPLEADO},
                {"Confirmar", constantesExepciones.ERROR_TRANSICION_CONFIRMAR_INCIDENCIA},
                {"Desestimar", constantesExepciones.ERROR_TRANSICION_DESESTIMAR_INCIDENCIA},
                {"IniciarProgreso", constantesExepciones.ERROR_TRANSICION_INICIAR_PROGRESO},
                {"Resolver", constantesExepciones.ERROR_TRANSICION_RESOLVER_INCIDENCIA}
        };
        Incidencia incidenciaReportada = new Incidencia(new CodigoCatalogo("1234-56"),
                LocalDate.of(2023, 4, 15),
                "",
                "Operador1",
                "reportador1",
                LocalDate.of(2023, 5, 29),
                "",
                new Solucionado());

        for (String[] data : testData) {
            String transicion = data[0];
            String msgException = String.format(data[1], "Solucionado");

            Exception e = assertThrows(Exception.class, () -> {
                switch (transicion) {
                    case "Asignar" -> incidenciaReportada.asignarEmpleado("");
                    case "Confirmar" -> incidenciaReportada.confirmarIncidencia();
                    case "Desestimar" -> incidenciaReportada.desestimarIncidencia();
                    case "IniciarProgreso" -> incidenciaReportada.iniciarProgreso();
                    case "Resolver" -> incidenciaReportada.resolverIncidencia();
                }
            });
            assertEquals(msgException, e.getMessage());
        }
    }

}
