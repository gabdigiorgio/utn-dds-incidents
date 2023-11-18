package org.utn.application;

import org.utn.presentation.incidents_load.CsvReader;
import org.utn.domain.job.Job;
import org.utn.persistence.job.JobsRepository;

import java.io.IOException;

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

    public Job getJob(Integer id) {
        Job job = jobsRepository.getById(id);
        return job;
    }

    public void processJob(Integer id, CsvReader csvReader) throws IOException {
        Job job = getJob(id);
        job.process(csvReader, job.getRawText());
    }
}
