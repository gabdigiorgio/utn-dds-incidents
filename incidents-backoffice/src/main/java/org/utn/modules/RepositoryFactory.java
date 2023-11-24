package org.utn.modules;

import org.utn.persistence.incident.DbIncidentsRepository;
import org.utn.persistence.incident.IncidentsRepository;
import org.utn.persistence.job.DbJobsRepository;
import org.utn.persistence.job.JobsRepository;
import org.utn.persistence.users.DbUsersRepository;
import org.utn.persistence.users.UsersRepository;

public class RepositoryFactory
{
    public static IncidentsRepository createIncidentRepository(){
        return new DbIncidentsRepository(PersistenceUtils.createEntityManagerFactory());
    }

    public static UsersRepository createUserRepository(){
        return new DbUsersRepository(PersistenceUtils.createEntityManagerFactory());
    }

    public static JobsRepository createJobRepository(){
        return new DbJobsRepository(PersistenceUtils.createEntityManagerFactory());
    }
}
