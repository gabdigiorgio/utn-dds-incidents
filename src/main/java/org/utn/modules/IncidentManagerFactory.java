package org.utn.modules;

import org.utn.application.IncidentManager;
import org.utn.domain.job.Job;

public class IncidentManagerFactory {
    public static IncidentManager createIncidentManager() {

        return new IncidentManager(RepositoryFactory.createIncidentRepository());
    }
}
