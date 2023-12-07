package org.utn.persistence;


import org.junit.Test;
import org.utn.domain.IncidentBuilderForTest;
import org.utn.domain.incident.Incident;
import org.utn.domain.users.Role;
import org.utn.domain.users.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class PersistenceIT {

    @Test
    public void SaveAndRetrieveIncident() throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("dbtest");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        User operator = new User("operator@gmail.com", "123", Role.OPERATOR, "123");
        User reportedBy = new User("user@gmail.com", "123", Role.USER, "123");

        entityManager.getTransaction().begin();
        entityManager.persist(operator);
        entityManager.persist(reportedBy);
        entityManager.getTransaction().commit();

        Incident incident = new IncidentBuilderForTest().withOperator(operator).withReportedBy(reportedBy).build();

        entityManager.getTransaction().begin();
        entityManager.persist(incident);
        entityManager.getTransaction().commit();
        entityManager.close();

        entityManagerFactory.close();
    }

}

