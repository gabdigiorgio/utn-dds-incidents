package org.utn.application;

import org.utn.domain.job.Job;
import org.utn.persistence.job.JobsRepository;

public class JobManager {

    private final JobsRepository jobsRepository;

    public JobManager(JobsRepository jobsRepository) {
        this.jobsRepository = jobsRepository;
    }

    public Job createJob(String rawText) {
        Job job = Job.create(rawText);
        jobsRepository.save(job);
        return job;
    }
}
