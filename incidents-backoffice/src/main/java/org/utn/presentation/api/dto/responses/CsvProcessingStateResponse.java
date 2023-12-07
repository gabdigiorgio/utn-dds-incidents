package org.utn.presentation.api.dto.responses;

import org.utn.domain.job.ProcessState;

public class CsvProcessingStateResponse {
    public ProcessState jobState;
    public String jobErrorMessage;

    public CsvProcessingStateResponse(ProcessState jobState, String jobErrorMessage) {
        this.jobState = jobState;
        this.jobErrorMessage = jobErrorMessage;
    }
}
