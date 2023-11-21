package org.utn.domain.job;

import org.utn.presentation.incidents_load.CsvReader;

import javax.persistence.*;
import java.io.Reader;
import java.io.StringReader;

@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 4000)
    private String rawText;
    private ProcessState state;
    private String errorMessage;

    public Job(Integer id, String rawText, ProcessState state) {
        this.id = id;
        this.rawText = rawText;
        this.state = state;
    }

    protected Job() {
        super();
    }

    public static Job create(String rawText) {
        return new Job(null, rawText, ProcessState.CREATED);
    }

    public Integer getId() {
        return id;
    }

    public String getRawText() { return rawText; }

    public ProcessState getState() {
        return state;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setState(ProcessState state) { this.state = state; }

    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public void process(CsvReader csvReader, String rawText) throws Exception {
        Reader reader = new StringReader(rawText);
        csvReader.execute(reader);
    }
}
