import org.junit.After;
import org.junit.Test;
import org.utn.estado.*;
import org.junit.Before;
import org.utn.incidente.Incidencia;

import static org.junit.Assert.assertEquals;

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
            Asignado asignado=new Asignado();

            estado.asignarEmpleado(incidencia,empleado);

            assertEquals(incidencia.getEstado().getClass(),asignado.getClass());
            assertEquals(incidencia.getEmpleado(),empleado);


        }
    @Test //despues probar por validacion y por creador=empleado
    public void deAsignadoAConfirmado(){
        Asignado asignado=new Asignado();
        String empleado="Jorge";
        Confirmado confirmado=new Confirmado();

        estado.asignarEmpleado(incidencia,empleado);
        asignado.confirmarIncidencia(incidencia);

        assertEquals(incidencia.getEstado().getClass(),confirmado.getClass());


    }

    @Test
    public void deAsignadoADesestimado(){
        Asignado asignado=new Asignado();
        String empleado="Jorge";
        Desestimado desestimado=new Desestimado();

        estado.asignarEmpleado(incidencia,empleado);
        asignado.desestimarIncidencia(incidencia);
//hay que revisar por que se desestima
        assertEquals(incidencia.getEstado().getClass(),desestimado.getClass());


    }
//casos no felices

}
