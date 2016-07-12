package com.graphaware.module.rabbitmq;

import com.graphaware.common.log.LoggerFactory;
import com.graphaware.runtime.config.TxDrivenModuleConfiguration;
import com.graphaware.runtime.module.thirdparty.WriterBasedThirdPartyIntegrationModule;
import com.graphaware.writer.thirdparty.ThirdPartyWriter;
import org.neo4j.logging.Log;

public class RabbitMQModule extends WriterBasedThirdPartyIntegrationModule {

    private static final Log LOG = LoggerFactory.getLogger(RabbitMQModule.class);

    private final RabbitMQConfiguration configuration;

    public RabbitMQModule(String moduleId, ThirdPartyWriter producer, RabbitMQConfiguration configuration) {
        super(moduleId, producer);
        this.configuration = configuration;
    }

    @Override
    public TxDrivenModuleConfiguration getConfiguration() {
        return configuration;
    }
}
