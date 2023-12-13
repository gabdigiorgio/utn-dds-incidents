package org.utn.presentation.api.dto.requests;

public class DismissIncidentRequest {
    private String rejectedReason;

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }
}
