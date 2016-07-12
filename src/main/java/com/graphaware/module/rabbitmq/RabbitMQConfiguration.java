package com.graphaware.module.rabbitmq;

import com.graphaware.common.policy.InclusionPolicies;
import com.graphaware.runtime.config.BaseTxDrivenModuleConfiguration;
import com.graphaware.runtime.policy.InclusionPoliciesFactory;

public class RabbitMQConfiguration extends BaseTxDrivenModuleConfiguration<RabbitMQConfiguration> {

    private static final InclusionPolicies DEFAULT_INCLUSION_POLICIES = InclusionPoliciesFactory.allBusiness();

    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PORT = "5672";
    private static final String DEFAULT_USER = "guest";
    private static final String DEFAULT_PASSWORD = "guest";
    private static final String DEFAULT_VHOST = "/";
    private static final String DEFAULT_EXCHANGE = "neo4j-exchange";

    private final String host;
    private final String port;
    private final String user;
    private final String password;
    private final String vhost;
    private final String exchange;

    private RabbitMQConfiguration(
            InclusionPolicies inclusionPolicies,
            String host,
            String port,
            String user,
            String password,
            String vhost,
            String exchange
    ) {
        super(inclusionPolicies, NEVER);
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.vhost = vhost;
        this.exchange = exchange;
    }

    @Override
    protected RabbitMQConfiguration newInstance(InclusionPolicies inclusionPolicies, long initializeUntil) {
        return new RabbitMQConfiguration(inclusionPolicies, getHost(), getPort(), getUser(), getPassword(), getVhost(), getExchange());
    }

    public static RabbitMQConfiguration defaultConfiguration() {
        return new RabbitMQConfiguration(
                DEFAULT_INCLUSION_POLICIES,
                DEFAULT_HOST,
                DEFAULT_PORT,
                DEFAULT_USER,
                DEFAULT_PASSWORD,
                DEFAULT_VHOST,
                DEFAULT_EXCHANGE
        );
    }

    public RabbitMQConfiguration withHost(String host) {
        return new RabbitMQConfiguration(getInclusionPolicies(), host, getPort(), getUser(), getPassword(), getVhost(), getExchange());
    }

    public RabbitMQConfiguration withPort(String port) {
        return new RabbitMQConfiguration(getInclusionPolicies(), getHost(), port, getUser(), getPassword(), getVhost(), getExchange());
    }

    public RabbitMQConfiguration withCredentials(String user, String password) {
        return new RabbitMQConfiguration(getInclusionPolicies(), getHost(), getPort(), user, password, getVhost(), getExchange());
    }

    public RabbitMQConfiguration withVirtualHost(String vhost) {
        return new RabbitMQConfiguration(getInclusionPolicies(), getHost(), getPort(), getUser(), getPassword(), vhost, getExchange());
    }

    public RabbitMQConfiguration withExchange(String exchange) {
        return new RabbitMQConfiguration(getInclusionPolicies(), getHost(), getPort(), getUser(), getPassword(), getVhost(), exchange);
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

    public String getVhost() {
        return vhost;
    }

    public String getExchange() {
        return exchange;
    }
}
