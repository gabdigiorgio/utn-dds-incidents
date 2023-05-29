import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.utn.dominio.estado.Asignado;
import org.utn.dominio.estado.Estado;
import org.utn.dominio.estado.Reportado;
import org.utn.dominio.incidencia.CodigoCatalogo;
import org.utn.dominio.incidencia.Incidencia;
import org.utn.persistencia.MemRepoIncidencias;
import org.utn.utils.exceptions.validador.FormatoCodigoCatalogoInvalidoException;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class TestRepoIncidencias {

    private static Incidencia incidencia1;
    private static Incidencia incidencia2;
    private static Incidencia incidencia3;
    private static Incidencia incidencia4;

    private static final MemRepoIncidencias repoIncidencias= MemRepoIncidencias.obtenerInstancia();
    private final String empleado="Jorge";

    private static void initializeRepoIncidencia() throws FormatoCodigoCatalogoInvalidoException {
        Estado estadoReportado = new Reportado();
        Estado estadoAsignado = new Asignado();
        incidencia1 = new Incidencia(new CodigoCatalogo("1234-56"),
                LocalDate.of(2023, 4, 15),
                "",
                "Operador1",
                "reportador1",
                LocalDate.of(2023, 5, 29),
                "",
                estadoReportado);
        incidencia2 = new Incidencia(new CodigoCatalogo("1533-24"),
                LocalDate.of(2023, 4, 13),
                "",
                "Operador2",
                "reportador2",
                LocalDate.of(2023, 4, 29),
                "", estadoAsignado);
        incidencia3 = new Incidencia(new CodigoCatalogo("7543-21"),
                LocalDate.of(2023, 4, 19),
                "",
                "Operador3",
                "reportador3",
                LocalDate.of(2023, 6, 29),
                "", estadoAsignado);
        incidencia4 = new Incidencia(new CodigoCatalogo("1533-24"),
                LocalDate.of(2023, 4, 10),
                "",
                "Operador4",
                "reportador4",
                LocalDate.of(2023, 2, 28),
                "", estadoAsignado);

    }

    @BeforeClass
    public static void initialize() throws FormatoCodigoCatalogoInvalidoException {
        initializeRepoIncidencia();
        repoIncidencias.save(incidencia1);
        repoIncidencias.save(incidencia2);
        repoIncidencias.save(incidencia3);
        repoIncidencias.save(incidencia4);

    }

    @After
    public void clean(){}

    @Test
    public void funcionalidadDeRepoIncidencia(){

        assertEquals(repoIncidencias.findByEstado("Asignado").size(),3);
        assertEquals(repoIncidencias.findByEstado("Reportado").size(),1);
        assertEquals(repoIncidencias.count(), 4);
    }
    //TEST DDE FILTROS EN REPOINCIDENCIAS
    @Test
    public void ultimosReportados(){

        assertEquals(repoIncidencias.obtenerIncidencias(3,"ordenarPorMasReciente").size(),3);
    }

    @Test
    public void desdeLaMasVieja(){

        assertEquals(repoIncidencias.obtenerIncidencias(5,"ordenarPorLaMasVieja").size(),4);
    }

    @Test
    public void ordenXestado(){
        //pido 4 incidencias pero solo hay 3 de ese estado
        assertEquals(repoIncidencias.obtenerIncidencias(4,new Asignado()).size(),3);

        assertEquals(repoIncidencias.obtenerIncidencias(3,new Reportado()).size(),1);
    }

    /*@Test
    public void ordenXLugar(){

        assertEquals(repoIncidencias.obtenerIncidencias(2,"1533-24").size(),2);
    }*/
}