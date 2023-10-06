package org.utn.presentation.worker;

import com.opencsv.exceptions.CsvException;
import com.rabbitmq.client.*;
import org.jetbrains.annotations.NotNull;
import org.utn.presentation.incidents_load.CsvReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class IncidentsCsvWorker extends DefaultConsumer {

    private String queueName;
    private CsvReader csvReader;

    public IncidentsCsvWorker(Channel channel, String queueName, CsvReader csvReader) {
        super(channel);
        this.queueName = queueName;
        this.csvReader = csvReader;
    }

    public void init() throws IOException {
        this.getChannel().queueDeclare(this.queueName, false, false, false, null);
        this.getChannel().basicConsume(this.queueName, false, this);
    }

    @Override
    public void handleDelivery(
            String consumerTag,
            Envelope envelope,
            AMQP.BasicProperties properties,
            byte[] body
    ) throws IOException {
        sendAck(envelope);
        Reader reader = createReader(body);
        try {
            System.out.println("Mensaje recibido en Worker");
            csvReader.execute(reader);
            System.out.println("Mensaje procesado correctamente en Worker");
        } catch (CsvException e) {
            System.out.println("Error a procesa el CSV en el Worker!!");
        }
    }

    @NotNull
    private static Reader createReader(byte[] body) throws UnsupportedEncodingException {
        String incidents = new String(body, "UTF-8");
        InputStream incidentsStream = new ByteArrayInputStream(incidents.getBytes(StandardCharsets.UTF_8));
        Reader reader = new InputStreamReader(incidentsStream);
        return reader;
    }

    private void sendAck(Envelope envelope) throws IOException {
        this.getChannel().basicAck(envelope.getDeliveryTag(), false);
    }

}

