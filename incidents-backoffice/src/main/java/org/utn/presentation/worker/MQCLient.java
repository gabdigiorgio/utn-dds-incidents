package org.utn.presentation.worker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MQCLient {

    private String host;
    private String username;
    private String password;
    private String virtualHost;
    private String queueName;
    private Connection connection ;
    private Channel channel;

    public MQCLient() throws IOException, TimeoutException {
        this.host = System.getenv("QUEUE_HOST");
        this.username =  System.getenv("QUEUE_USERNAME");
        this.password =  System.getenv("QUEUE_PASSWORD");
        this.virtualHost = System.getenv("QUEUE_USERNAME");
        this.queueName =  System.getenv("QUEUE_NAME");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(this.host);
        factory.setUsername(this.username);
        factory.setPassword(this.password);
        factory.setVirtualHost(this.virtualHost);
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();
    }

    public void publish(String message) throws IOException {
        channel.basicPublish("", this.queueName, null, message.getBytes());
    }
}

