package org.utn.modules;

import org.utn.application.IncidentManager;

public class ManagerFactory {
    public static IncidentManager createIncidentManager() {
        return new IncidentManager(RepositoryFactory.createIncidentRepository());
    }
}
