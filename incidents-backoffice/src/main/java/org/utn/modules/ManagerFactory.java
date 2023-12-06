package org.utn.modules;

import org.utn.application.IncidentCsvManager;
import org.utn.application.IncidentManager;
import org.utn.application.JobManager;
import org.utn.application.UserManager;

public class ManagerFactory {
    public static IncidentManager createIncidentManager() {
        return new IncidentManager(RepositoryFactory.createIncidentRepository(), ServiceFactory.createInventoryService());
    }

    public static IncidentCsvManager createIncidentCsvManager() {
        return new IncidentCsvManager(RepositoryFactory.createIncidentRepository(), ServiceFactory.createInventoryService());
    }

    public static UserManager createUserManager() {
        return new UserManager(RepositoryFactory.createUserRepository());
    }

    public static JobManager createJobManager() {
        return new JobManager(RepositoryFactory.createJobRepository());
    }
}
