/**
 * Copyright 2022 Timo Geusch (timo@lonecppcoder.com)
 *
 * Licensed under Apache Licence, Version 2.0
 *
 * Please see the LICENSE file in the project's root directory for
 * details of the license.
 *
 */
package com.lonecppcoder.kafka.config;

import org.apache.kafka.common.config.ConfigData;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;
import java.util.HashMap;
import java.util.TreeSet;

import org.junit.Test;

public class AppTest
{
    private static final long ttl = 45000L;

    @Test
    public void tryInstantiateConfigProvider() {
        TTLFileConfigProvider p = new TTLFileConfigProvider();
        assertTrue( p != null);
    }

    @Test
    public void tryRetrieveConfigItem() {
        TTLFileConfigProvider p = new TTLFileConfigProvider();
        assertNotNull(p);
        Map<String, Object> configs = new HashMap<String, Object>();
        configs.put("ttlfileconfig.ttl.ms", ttl);
        p.configure(configs);

        ConfigData d = p.get("src/test/resources/test-config.properties");
        assertNotNull(d);
        assertNotNull(d.data());
        long setTTL = d.ttl();
        assertEquals(ttl, setTTL);
        assertEquals(d.data().get("test.uri"), "mongodb://localhost/");
    }

    @Test
    public void tryRetrieveConfigItemSet() {
        TreeSet<String> keys = new TreeSet<String>();
        keys.add("test.uri");

        TTLFileConfigProvider p = new TTLFileConfigProvider();
        Map<String, Object> configs = new HashMap<String, Object>();
        configs.put("ttlfileconfig.ttl.ms", ttl);
        assertNotNull(p);
        p.configure(configs);

        ConfigData d = p.get("src/test/resources/test-config.properties", keys);
        assertNotNull(d);
        assertNotNull(d.data());
        long setTTL = d.ttl();
        assertEquals(ttl, setTTL);
        String testUri = d.data().get("test.uri");
        assertEquals("mongodb://localhost/", testUri);
    }
}
