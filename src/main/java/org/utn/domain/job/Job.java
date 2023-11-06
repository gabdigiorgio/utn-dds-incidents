package org.utn.domain.job;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private ProcessState state;
    private String rawText;

    public Job(Integer id, String rawText, ProcessState state) {
        this.id = id;
    }

    public static Job create(String rawText) {
        return new Job(null, rawText, ProcessState.CREATED);
    }

    public Integer getId() {
        return id;
    }
}
