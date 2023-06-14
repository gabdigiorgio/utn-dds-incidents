package org.utn.persistencia;


import org.junit.Test;
import org.utn.dominio.IncidenciasBuilderForTest;
import org.utn.dominio.incidencia.Incidencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class PersistenceIT {

    @Test
    public void testGuardarYRecuperarDoc() throws Exception {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("copiamedb");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Incidencia incidencia= new IncidenciasBuilderForTest().build();
        incidencia.setEmpleado("Juancito");

        entityManager.getTransaction().begin();
        entityManager.persist(incidencia);
        entityManager.getTransaction().commit();
        entityManager.close();

        entityManager = entityManagerFactory.createEntityManager();

        entityManager.close();
        entityManagerFactory.close();
    }

}

