package org.utn.persistence.job;

import org.utn.domain.job.Job;

public interface JobsRepository {
    void save(Job job);

    Job getById(Integer id);
}
