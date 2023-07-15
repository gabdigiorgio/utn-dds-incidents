package org.utn.aplicacion;

import com.rabbitmq.client.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.utn.presentacion.carga_incidentes.ReaderCsv;

import javax.persistence.EntityManagerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.util.Map;

public class Worker extends DefaultConsumer {

    private String queueName;
    private EntityManagerFactory entityManagerFactory;

    protected Worker(Channel channel, String queueName) {
        super(channel);
        this.queueName = queueName;
        this.entityManagerFactory = entityManagerFactory;
    }

    private void init() throws IOException {
        // Declarar la cola desde la cual consumir mensajes
        this.getChannel().queueDeclare(this.queueName, false, false, false, null);
        // Consumir mensajes de la cola
        this.getChannel().basicConsume(this.queueName, false, this);
    }

    @Override
    public void handleDelivery(
            String consumerTag,
            Envelope envelope,
            AMQP.BasicProperties properties,
            byte[] body
    ) throws IOException {
        // Confirmar la recepción del mensaje a la mensajeria
        this.getChannel().basicAck(envelope.getDeliveryTag(), false);
        String incidents = new String(body, "UTF-8");
        System.out.println("se recibio el siguiente payload:");
        System.out.println(incidents);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {

            JSONObject json = new JSONObject();
            json.put("incidents", incidents);

            StringEntity params = new StringEntity(json.toString());
            HttpUriRequest httpPost = RequestBuilder.post()
                    .setUri(new URI("https://tpa-utn-grupo-1.onrender.com/incidents/process_csv"))
                    .addHeader("Content-Type", "application/json; charset=UTF-8")
                    .addHeader("Accept", "*/*")
                    .addHeader("Accept-Encoding", "gzip, deflate, br")
                    .setEntity(params)
                    .build();

            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                System.out.println(EntityUtils.toString(response.getEntity()));
            } finally {
                response.close();
            }



        } catch(Exception error) {
            httpclient.close();
            System.out.println("Error al procesar payload!!");
        }
    }

    public static void main(String[] args) throws Exception {
        // Establecer la conexión con CloudAMQP
        Map<String, String> env = System.getenv();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(env.get("QUEUE_HOST"));
        factory.setUsername(env.get("QUEUE_USERNAME"));
        factory.setPassword(env.get("QUEUE_PASSWORD"));

        // En el plan más barato, el VHOST == USER
        factory.setVirtualHost(env.get("QUEUE_USERNAME"));
        String queueName = env.get("QUEUE_NAME");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        Worker worker = new Worker(channel,queueName);
        worker.init();
    }

}

