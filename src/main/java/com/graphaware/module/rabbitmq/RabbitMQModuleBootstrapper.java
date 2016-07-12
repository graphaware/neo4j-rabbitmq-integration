package com.graphaware.module.rabbitmq;

import com.graphaware.common.log.LoggerFactory;
import com.graphaware.runtime.module.BaseRuntimeModuleBootstrapper;
import com.graphaware.runtime.module.RuntimeModule;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.logging.Log;

import java.util.Map;

public class RabbitMQModuleBootstrapper extends BaseRuntimeModuleBootstrapper<RabbitMQConfiguration> {

    private static final Log LOG = LoggerFactory.getLogger(RabbitMQModuleBootstrapper.class);

    @Override
    protected RabbitMQConfiguration defaultConfiguration() {
        return RabbitMQConfiguration.defaultConfiguration();
    }

    @Override
    protected RuntimeModule doBootstrapModule(String moduleId, Map<String, String> config, GraphDatabaseService database, RabbitMQConfiguration configuration) {
        return new RabbitMQModule(moduleId, createProducer(configuration), configuration);
    }

    protected RabbitMQProducer createProducer(RabbitMQConfiguration configuration) {
        return new RabbitMQProducer(configuration);
    }
}
