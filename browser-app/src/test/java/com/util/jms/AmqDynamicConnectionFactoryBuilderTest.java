package com.util.jms;

import org.junit.Assert;
import org.junit.Test;

import javax.jms.ConnectionFactory;

public class AmqDynamicConnectionFactoryBuilderTest {

    @Test
    public void testGetFactory() throws Exception {
        ConnectionFactory cf = new AmqDynamicConnectionFactoryBuilder().getFactory();
        Assert.assertNotNull("Null factory", cf);
        Assert.assertNotNull("Null Connection", cf.createConnection());
    }

}
