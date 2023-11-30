package org.utn.application;

import org.utn.domain.job.Job;
import org.utn.domain.job.ProcessState;
import org.utn.domain.job.JobsRepository;
import org.utn.presentation.incidents_load.CsvReader;

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

    public void processJob(Integer id, CsvReader csvReader) {
        Job job = getJob(id);
        updateJobProcessingState(id, ProcessState.IN_PROCESS);
        try {
            job.process(csvReader, job.getRawText());
            updateJobProcessingState(id, ProcessState.DONE);
        } catch (Exception e) {
            String jobErrorMessage = e.getMessage();
            updateJobErrorMessage(id, jobErrorMessage);
            updateJobProcessingState(id, ProcessState.DONE_WITH_ERRORS);
            System.out.println("Error al procesar el CSV en el Worker!!");
            throw new RuntimeException(e);
        }
    }

    public void updateJobProcessingState(Integer id, ProcessState state) {
        Job job = getJob(id);
        job.setState(state);
        jobsRepository.update(job);
    }

    public void updateJobErrorMessage(Integer id, String errorMessage) {
        Job job = getJob(id);
        job.setErrorMessage(errorMessage);
        jobsRepository.update(job);
    }
}
