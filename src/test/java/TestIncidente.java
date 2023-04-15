import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.utn.dominio.estado.*;
import org.utn.dominio.incidente.Incidencia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void deReportadoAAsignado(){
        String empleado="Jorge";

        incidencia.asignarEmpleado(empleado);

        assertTrue(incidencia.getEstado() instanceof Asignado);
        assertEquals(incidencia.getEmpleado(),empleado);
    }
    @Test //despues probar por validacion y por creador=empleado
    public void deAsignadoAConfirmado(){
        Asignado asignado=new Asignado();
        String empleado="Jorge";
        Confirmado confirmado=new Confirmado();

        incidencia.asignarEmpleado(empleado);
        asignado.confirmarIncidencia(incidencia);

        assertEquals(incidencia.getEstado().getClass(),confirmado.getClass());
    }

    @Test
    public void deAsignadoADesestimado(){
        Asignado asignado=new Asignado();
        String empleado="Jorge";
        Desestimado desestimado=new Desestimado();

        incidencia.asignarEmpleado(empleado);
        asignado.desestimarIncidencia(incidencia);
        //hay que revisar por que se desestima
        assertEquals(incidencia.getEstado().getClass(),desestimado.getClass());


    }
//casos no felices

}
