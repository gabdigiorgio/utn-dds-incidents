package org.utn.modules;

import org.utn.application.incident.IncidentMassiveManager;
import org.utn.application.incident.IncidentManager;
import org.utn.application.job.JobManager;
import org.utn.application.users.UserManager;

public class ManagerFactory {
    public static IncidentManager createIncidentManager() {
        return new IncidentManager(RepositoryFactory.createIncidentRepository(), ServiceFactory.createInventoryService());
    }

    public static IncidentMassiveManager createIncidentCsvManager() {
        return new IncidentMassiveManager(RepositoryFactory.createIncidentRepository(), ServiceFactory.createInventoryService());
    }

    public static UserManager createUserManager() {
        return new UserManager(RepositoryFactory.createUserRepository(), ServiceFactory.createPasswordHasher());
    }

    public static JobManager createJobManager() {
        return new JobManager(RepositoryFactory.createJobRepository());
    }
}
