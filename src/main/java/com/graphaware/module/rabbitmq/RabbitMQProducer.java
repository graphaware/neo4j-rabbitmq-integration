package com.graphaware.module.rabbitmq;

import com.graphaware.common.log.LoggerFactory;
import com.graphaware.common.representation.NodeRepresentation;
import com.graphaware.writer.thirdparty.BaseThirdPartyWriter;
import com.graphaware.writer.thirdparty.NodeCreated;
import com.graphaware.writer.thirdparty.WriteOperation;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.neo4j.logging.Log;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class RabbitMQProducer extends BaseThirdPartyWriter {

    private static final Log LOG = LoggerFactory.getLogger(RabbitMQProducer.class);

    private final String host;
    private final int port;
    private final String user;
    private final String password;
    private final String vhost;
    private final String exchange;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private Connection connection;
    private Channel channel;

    public RabbitMQProducer(RabbitMQConfiguration configuration) {
        this.host = configuration.getHost();
        this.port = Integer.valueOf(configuration.getPort());
        this.user = configuration.getUser();
        this.password = configuration.getPassword();
        this.vhost = "/";
        this.exchange = configuration.getExchange();
    }

    @Override
    public void start() {
        LOG.debug("Starting RabbitMQ Producer");
        super.start();
        //createChannel();
    }

    @Override
    public void stop() {
        super.stop();
        closeChannel();
    }

    @Override
    protected void processOperations(List<Collection<WriteOperation<?>>> list) {
        for (Collection<WriteOperation<?>> writeOperations : list) {
            for (WriteOperation<?> operation : writeOperations) {
                if (operation.getType() == WriteOperation.OperationType.NODE_CREATED) {
                    String json = getJsonOperation(operation);
                    if (!json.equals("")) {
                        byte[] messageBytes = json.getBytes();
                        try {
                            LOG.debug("Received created node : {}", json);
                            getChannel().basicPublish(exchange, "", null, messageBytes);
                        } catch (IOException e) {
                            LOG.warn("Unable to publish message");
                        }
                    }
                }
            }
        }
    }

    private String getJsonOperation(WriteOperation<?> operation) {
        NodeRepresentation representation = ((NodeCreated) operation).getDetails();
        try {
            String json = objectMapper.writeValueAsString(representation);

            return json;
        } catch (IOException e) {
            LOG.error("Could not serialize node created to json, {}", e.getMessage());
        }

        return "";
    }

    private Channel getChannel() {
        if (null == channel) {
            createChannel();
        }

        return channel;
    }

    private void createChannel() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(user);
        factory.setPassword(password);
        factory.setHost(host);
        factory.setPort(port);
        factory.setVirtualHost(vhost);
        try {
            Thread.sleep(20000);
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(exchange, "direct", true);
        } catch (IOException | TimeoutException | InterruptedException e) {
            LOG.error("Unable to connect to RabbitMQ");
        }
    }

    private void closeChannel() {
        if (null == channel) {
            return;
        }
        try {
            channel.close();
        } catch (IOException | TimeoutException e) {
            LOG.error("Unable to close RabbitMQ channel");
        }
    }
}
