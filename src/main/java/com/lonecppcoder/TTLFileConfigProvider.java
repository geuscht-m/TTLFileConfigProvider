/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lonecppcoder.kafka.config;

import org.apache.kafka.common.config.ConfigData;
import org.apache.kafka.common.config.ConfigException;
import org.apache.kafka.common.config.provider.ConfigProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * An implementation of {@link ConfigProvider} that represents a Properties file.
 * All property keys and values are stored as cleartext.
 */
public class TTLFileConfigProvider implements ConfigProvider {

    private static final Logger log = LoggerFactory.getLogger(TTLFileConfigProvider.class);
    private TTLFileConfigProviderConfig configData;

    public void configure(Map<String, ?> configs) {
        log.info("Building configuration from {}", configs);
        this.configData = new TTLFileConfigProviderConfig(configs);
    }

    /**
     * Retrieves the data at the given Properties file.
     *
     * @param path the file where the data resides
     * @return the configuration data
     */
    public ConfigData get(String path) {
        log.info("Retrieving config data from path '{}'", path);

        Map<String, String> data = new HashMap<>();
        if (path == null || path.isEmpty()) {
            return new ConfigData(data);
        }
        try (Reader reader = reader(path)) {
            log.info("Reloading configuration data from {}", path);
            Properties properties = new Properties();
            properties.load(reader);
            Enumeration<Object> keys = properties.keys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement().toString();
                String value = properties.getProperty(key);
                if (value != null) {
                    data.put(key, value);
                }
            }
            return new ConfigData(data, this.configData.maxSecretTTL);
        } catch (IOException e) {
            log.error("Could not read properties from file {}", path, e);
            throw new ConfigException("Could not read properties from file " + path);
        }
    }

    /**
     * Retrieves the data with the given keys at the given Properties file.
     *
     * @param path the file where the data resides
     * @param keys the keys whose values will be retrieved
     * @return the configuration data
     */
    public ConfigData get(String path, Set<String> keys) {
        log.info("Retrieving config data from path '{}'", path);
        Map<String, String> data = new HashMap<>();
        if (path == null || path.isEmpty()) {
            return new ConfigData(data);
        }
        try (Reader reader = reader(path)) {
            Properties properties = new Properties();
            properties.load(reader);
            for (String key : keys) {
                String value = properties.getProperty(key);
                if (value != null) {
                    data.put(key, value);
                }
            }
            log.info("Returning new config data with TTL '{}'", this.configData.maxSecretTTL);
            return new ConfigData(data, this.configData.maxSecretTTL);
        } catch (IOException e) {
            log.error("Could not read properties from file {}", path, e);
            throw new ConfigException("Could not read properties from file " + path);
        }
    }

    // visible for testing
    protected Reader reader(String path) throws IOException {
        return Files.newBufferedReader(Paths.get(path));
    }

    public void close() {
    }
}
