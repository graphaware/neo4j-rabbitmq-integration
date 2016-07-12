package com.graphaware.module.rabbitmq;

import com.graphaware.common.log.LoggerFactory;
import com.graphaware.common.policy.NodeInclusionPolicy;
import com.graphaware.runtime.config.function.StringToNodeInclusionPolicy;
import com.graphaware.runtime.module.BaseRuntimeModuleBootstrapper;
import com.graphaware.runtime.module.RuntimeModule;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.logging.Log;

import java.util.Map;

public class RabbitMQModuleBootstrapper extends BaseRuntimeModuleBootstrapper<RabbitMQConfiguration> {

    private static final Log LOG = LoggerFactory.getLogger(RabbitMQModuleBootstrapper.class);

    private static final String NODE = "node";
    private static final String RELATIONSHIP = "relationship";
    private static final String HOST = "host";
    private static final String PORT = "port";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String VHOST = "vhost";
    private static final String EXCHANGE = "exchange";

    @Override
    protected RabbitMQConfiguration defaultConfiguration() {
        return RabbitMQConfiguration.defaultConfiguration();
    }

    @Override
    protected RabbitMQConfiguration configureInclusionPolicies(Map<String, String> config, RabbitMQConfiguration configuration) {
        return configuration;
    }

    @Override
    protected RuntimeModule doBootstrapModule(String moduleId, Map<String, String> config, GraphDatabaseService database, RabbitMQConfiguration configuration) {
        if (configExists(config, NODE)) {
            NodeInclusionPolicy policy = StringToNodeInclusionPolicy.getInstance().apply(config.get(NODE));
            LOG.info("Node inclusion policy set to %s", policy);
            configuration = configuration.with(policy);
        }

        if (configExists(config, HOST)) {
            String host = config.get(HOST);
            LOG.info("Host property set to %s", host);
            configuration = configuration.withHost(host);
        }

        if (configExists(config, PORT)) {
            String port = config.get(PORT);
            LOG.info("Port property set to %s", port);
            configuration = configuration.withPort(port);
        }

        if (configExists(config, USER) && configExists(config, PASSWORD)) {
            configuration = configuration.withCredentials(config.get(USER), config.get(PASSWORD));
        }

        if (configExists(config, VHOST)) {
            String vhost = config.get(VHOST);
            LOG.info("vhost property set to %s", vhost);
            configuration = configuration.withVirtualHost(vhost);
        }

        if (configExists(config, EXCHANGE)) {
            String exchange = config.get(EXCHANGE);
            LOG.info("Exchange property set to %s", exchange);
            configuration = configuration.withExchange(exchange);
        }

        return new RabbitMQModule(moduleId, createProducer(configuration), configuration);
    }

    protected RabbitMQProducer createProducer(RabbitMQConfiguration configuration) {
        return new RabbitMQProducer(configuration);
    }
}
