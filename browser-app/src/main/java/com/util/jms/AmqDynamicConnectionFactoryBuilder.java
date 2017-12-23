package com.util.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;
import java.util.Properties;

import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Named
public class AmqDynamicConnectionFactoryBuilder { 
    private static final Logger log = LoggerFactory.getLogger(AmqDynamicConnectionFactoryBuilder.class);

    @Value("${activemq.host:tcp://localhost:61616}")
    private String host;
    @Value("${activemq.user:admin}")
    private String principle;
    @Value("${activemq.password:admin}")
    private String credential;
    
    private ConnectionFactory factory;
    
    public AmqDynamicConnectionFactoryBuilder() {}
    
    public ConnectionFactory getFactory() {
        return Optional.ofNullable(factory).orElseGet(() -> createFactory());
    }

    public ConnectionFactory createFactory() {
        Properties props = new Properties(); 
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL, host);
        props.setProperty(Context.SECURITY_PRINCIPAL, principle);
        props.setProperty(Context.SECURITY_CREDENTIALS, credential);
        try {
            Context ctx = new InitialContext(props);
            factory = (ConnectionFactory)ctx.lookup("ConnectionFactory");
        } catch (NamingException e) {
            log.error("Failed to intialize connection factory.", e);
            throw new InstantiationError("Unable to create connection factory, check the configuration and try again.");
        }
        return factory;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPrinciple(String principle) {
        this.principle = principle;
    }
    
    public void setCredential(String credential) {
        this.credential = credential;
    }
}
