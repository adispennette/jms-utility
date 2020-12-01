package com.util.jms;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.ConnectionFactory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
public class AmqDynamicConnectionFactoryBuilderTest extends BrokerTest {
    @Autowired
    AmqDynamicConnectionFactoryBuilder builder;

    @Test
    public void testGetFactory() throws Exception {
        ConnectionFactory cf = builder.getFactory();
        Assert.assertNotNull("Null factory", cf);
        Assert.assertNotNull("Null Connection", cf.createConnection());
    }

}
