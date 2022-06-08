package com.lonecppcoder.kafka.config;

import org.apache.kafka.common.config.ConfigData;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void tryInstantiateConfigProvider() {
        TTLFileConfigProvider p = new TTLFileConfigProvider();
        assertTrue( p != null);
    }

    @Test
    public void tryRetrieveConfigItem() {
        TTLFileConfigProvider p = new TTLFileConfigProvider();

        ConfigData d = p.get("src/test/resources/test-config.properties");
        System.out.println(d.data().toString());
        System.out.flush();
    }
}
