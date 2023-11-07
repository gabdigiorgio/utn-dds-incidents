package org.utn.presentation.worker;

import com.opencsv.exceptions.CsvException;
import com.rabbitmq.client.*;
import org.jetbrains.annotations.NotNull;
import org.utn.domain.job.Job;
import org.utn.modules.ManagerFactory;
import org.utn.modules.PersistenceUtils;
import org.utn.modules.RepositoryFactory;
import org.utn.persistence.job.JobsRepository;
import org.utn.presentation.incidents_load.CsvReader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class IncidentsCsvWorker extends DefaultConsumer {

    private String queueName;
    private EntityManagerFactory entityManagerFactory;
    private CsvReader csvReader;

    public IncidentsCsvWorker(Channel channel, String queueName, EntityManagerFactory entityManagerFactory, CsvReader csvReader) {
        super(channel);
        this.queueName = queueName;
        this.entityManagerFactory = entityManagerFactory;
        this.csvReader = csvReader;
    }

    public void init() throws IOException {
        this.getChannel().queueDeclare(this.queueName, false, false, false, null);
        this.getChannel().basicConsume(this.queueName, false, this);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
            throws IOException {

        sendAck(envelope);
        String jobId = new String(body, "UTF-8");

        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            JobsRepository jobRepository = RepositoryFactory.createJobRepository();

            Job job = jobRepository.getById(Integer.parseInt(jobId));

            job.process(csvReader, job.getRawText());

            entityManager.getTransaction().commit();

            entityManager.close();
        } catch (CsvException e) {
            System.out.println("Error al procesar el CSV en el Worker!!");
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
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

    public static void main(String[] args) throws IOException, TimeoutException {
        String[] requiredEnvVars = {"QUEUE_HOST", "QUEUE_USERNAME", "QUEUE_PASSWORD", "QUEUE_NAME"};

        Map<String, String> env = System.getenv();

        //forzamos un fail fast acá por si las variables no están cargadas correctamente
        //me salto este error porque no las tenia cargadas en el proyecto...
        for (String var : requiredEnvVars) {
            if (env.get(var) == null) {
                throw new IllegalArgumentException("Falta la variable de entorno: " + var);
            }
        }

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(env.get("QUEUE_HOST"));
        factory.setUsername(env.get("QUEUE_USERNAME"));
        factory.setPassword(env.get("QUEUE_PASSWORD"));
        factory.setVirtualHost(env.get("QUEUE_USERNAME"));
        String queueName = env.get("QUEUE_NAME");

        try{
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            EntityManagerFactory entityManagerFactory =  PersistenceUtils.createEntityManagerFactory();
            IncidentsCsvWorker worker = new IncidentsCsvWorker(channel, queueName, entityManagerFactory,new CsvReader(ManagerFactory.createIncidentManager()));
            worker.init();
        } catch (AuthenticationFailureException afe) {
            throw new AuthenticationFailureException("Error en la validacion de las credenciales del Worker : " + afe.getMessage());
        }

    }
}

