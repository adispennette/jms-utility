package com.util.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

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
    
    public Collection<String> browse(String queueName) throws JMSException {
        Connection con = jmsConnectionFactory.createConnection();
        try {
            con.start();
            Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);
            QueueBrowser browser = session.createBrowser(queue);
            Enumeration<Message> e = browser.getEnumeration();
            List<String> messages = new ArrayList<>();
            System.out.println(e.hasMoreElements());
            while (e.hasMoreElements()) {
                messages.add(((TextMessage)e.nextElement()).getText());
            }
            session.close();
            return messages;
        } finally {
            con.close();
        }
    }
    
    public void send(String queueName, String payload) throws Exception {
        Connection con = jmsConnectionFactory.createConnection();
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
