package org.utn.domain.job;

import com.opencsv.exceptions.CsvException;
import org.utn.presentation.incidents_load.CsvReader;

import javax.persistence.*;
import java.io.IOException;
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

    private ProcessState getState() {
        return state;
    }

    private void setState(ProcessState state) { this.state = state; }

    public void process(CsvReader csvReader, String rawText) throws IOException {
        setState(ProcessState.IN_PROCESS);
        try(Reader reader = new StringReader(rawText)) {
            csvReader.execute(reader);
            setState(ProcessState.DONE);
        }
        catch (CsvException e) {
            setState(ProcessState.DONE_WITH_ERRORS);
            System.out.println("Error al procesar el CSV en el Worker!!");
        }

    }
}
