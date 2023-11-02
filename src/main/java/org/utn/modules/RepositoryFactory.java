package org.utn.modules;

import org.utn.persistence.DbIncidentsRepository;
import org.utn.persistence.IncidentsRepository;

public class RepositoryFactory {

    public static IncidentsRepository createIncidentRepository(){
        return new DbIncidentsRepository(PersistenceUtils.createEntityManagerFactory().createEntityManager());
    }
}
