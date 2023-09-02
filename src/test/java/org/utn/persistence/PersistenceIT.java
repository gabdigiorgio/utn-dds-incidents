package org.utn.persistence;


import org.junit.Test;
import org.utn.domain.IncidentBuilderForTest;
import org.utn.domain.incident.Incident;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class PersistenceIT {

    @Test
    public void SaveAndRetrieveIncident() throws Exception {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("dbtest");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Incident incident= new IncidentBuilderForTest().build();
        incident.setEmployee("Juancito");

        entityManager.getTransaction().begin();
        entityManager.persist(incident);
        entityManager.getTransaction().commit();
        entityManager.close();

        entityManager = entityManagerFactory.createEntityManager();

        entityManager.close();
        entityManagerFactory.close();
    }

}

