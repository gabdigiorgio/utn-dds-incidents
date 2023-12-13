package org.utn.application;

import org.utn.domain.users.Role;
import org.utn.domain.users.User;
import org.utn.presentation.api.dto.requests.EditIncidentRequest;

public class EditIncident {
    private String reportDate;
    private String description;
    private Integer editorId;
    private Role editorRole;

    public EditIncident(EditIncidentRequest editIncidentRequest, User editor) {
        this.reportDate = editIncidentRequest.getReportDate();
        this.description = editIncidentRequest.getDescription();
        this.editorId = editor.getId();
        this.editorRole = editor.getRole();
    }

    public String getReportDate() {
        return reportDate;
    }

    public String getDescription() {
        return description;
    }

    public Integer getEditorId() {
        return editorId;
    }

    public Role getEditorRole() {
        return editorRole;
    }
}
