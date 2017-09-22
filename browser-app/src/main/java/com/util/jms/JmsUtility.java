package com.util.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

@Named
public class JmsUtility {
    private static final Logger log = LoggerFactory.getLogger(JmsUtility.class);

    @Inject
    private ConnectionFactory jmsConnectionFactory;

    public JmsUtility() {}
    
    public void browse(String queueName) throws JMSException {
        Connection con = jmsConnectionFactory.createConnection("admin", "admin");
        try {
            con.start();
            Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);
            QueueBrowser browser = session.createBrowser(queue);
            Enumeration<Message> e = browser.getEnumeration();
            while (e.hasMoreElements()) {
                Message message = e.nextElement();
                System.out.println("\n\nmessage: ");
                System.out.println(((TextMessage)message).getText());
                Enumeration<String> keys = message.getPropertyNames();
                while (keys.hasMoreElements()) System.out.println(keys.nextElement());
                System.out.println("\n\n");
            }
            session.close();
        } finally {
            con.close();
        }
    }
    
    public void send(String queueName, String payload) throws Exception {
        //{"id":2023589,"dateTime":"2017-09-19T20:05:03.980Z","payload":"{\"timestamp\":\"2017-09-19T20:05:04.287Z\",\"transferId\":\"20170919000266\",\"externalTransferId\":\"145|83|64\",\"direction\":\"INCOMING\",\"transferMechanism\":\"ACH\",\"account\":\"5RS12063\",\"correspondentCode\":\"SOFI\",\"amount\":45000.0,\"status\":\"COMPLETE\",\"reason\":null}"}
        Connection con = jmsConnectionFactory.createConnection("admin", "admin");
        try {
            log.info("Starting jms connection.");
            con.start();
            Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(queue);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            log.info("Sending jms message: {}", payload);
            producer.send(session.createTextMessage(payload));
            session.close();
        } finally {
            log.info("Closing jms connection.");
            con.close();
        }
    }
}
