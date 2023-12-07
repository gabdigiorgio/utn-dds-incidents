package org.utn.domain.job;

import org.utn.domain.users.User;
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
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    public Job(Integer id, String rawText, User creator, ProcessState state) {
        this.id = id;
        this.rawText = rawText;
        this.creator = creator;
        this.state = state;
    }

    protected Job() {
        super();
    }

    public static Job create(String rawText, User creator) {
        return new Job(null, rawText, creator, ProcessState.CREATED);
    }

    public Integer getId() {
        return id;
    }

    public String getRawText() {
        return rawText;
    }

    public ProcessState getState() {
        return state;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void process(CsvReader csvReader, String rawText) throws Exception {
        Reader reader = new StringReader(rawText);
        csvReader.execute(reader, this.creator);
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
