package com.util.jms;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.net.URI;

public class BrokerTest {
    private static BrokerService broker;

    @BeforeClass
    public static void setup() throws Exception {
        broker = new BrokerService();
        TransportConnector connector = new TransportConnector();
        connector.setUri(new URI("tcp://localhost:61616"));
        broker.addConnector(connector);
        broker.start();
    }

    @AfterClass
    public static void end() throws Exception {
        broker.stop();
    }
}
