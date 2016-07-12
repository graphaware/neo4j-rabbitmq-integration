package com.graphaware.module.rabbitmq;

import com.graphaware.common.policy.InclusionPolicies;
import com.graphaware.runtime.config.BaseTxDrivenModuleConfiguration;
import com.graphaware.runtime.policy.InclusionPoliciesFactory;

public class RabbitMQConfiguration extends BaseTxDrivenModuleConfiguration<RabbitMQConfiguration> {

    private static final InclusionPolicies DEFAULT_INCLUSION_POLICIES = InclusionPoliciesFactory.allBusiness();

    private final String host;
    private final String port;
    private final String user;
    private final String password;
    private final String exchange;

    @Override
    protected RabbitMQConfiguration newInstance(InclusionPolicies inclusionPolicies, long l) {
        return null;
    }

    public static RabbitMQConfiguration defaultConfiguration() {
        return new RabbitMQConfiguration(
                DEFAULT_INCLUSION_POLICIES,
                "rabbit",
                "5672",
                "guest",
                "guest",
                "/",
                "neo4j-exchange"
        );
    }

    private RabbitMQConfiguration(InclusionPolicies inclusionPolicies, String host, String port, String user, String password, String vhost, String exchange) {
        super(inclusionPolicies);
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.exchange = exchange;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getExchange() {
        return exchange;
    }
}
