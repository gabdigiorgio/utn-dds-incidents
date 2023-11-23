package org.utn.modules;

import org.utn.application.IncidentManager;
import org.utn.application.JobManager;

public class ManagerFactory {
    public static IncidentManager createIncidentManager() {
        return new IncidentManager(RepositoryFactory.createIncidentRepository(), ServiceFactory.createInventoryService());
    }

    public static JobManager createJobManager() {
        return new JobManager(RepositoryFactory.createJobRepository());
    }
}
