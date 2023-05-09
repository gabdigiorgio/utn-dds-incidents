import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.utn.dominio.estado.Asignado;
import org.utn.dominio.estado.Estado;
import org.utn.dominio.estado.Reportado;
import org.utn.dominio.incidente.Incidencia;
import org.utn.infraestructura.persistencia.MemRepoIncidencias;

import static org.junit.Assert.assertEquals;

public class TestRepoIncidencias {

    private Incidencia incidencia1;
    private Incidencia incidencia2;
    private Incidencia incidencia3;
    private Incidencia incidencia4;

    private final MemRepoIncidencias repoIncidencias= MemRepoIncidencias.obtenerInstancia();
    private final String empleado="Jorge";

    private void initializeRepoIncidencia(){
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
        this.initializeRepoIncidencia();
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

    @Test
    public void ordenXLugar(){

        assertEquals(repoIncidencias.obtenerIncidencias(2,"1533-24").size(),2);
    }
}
