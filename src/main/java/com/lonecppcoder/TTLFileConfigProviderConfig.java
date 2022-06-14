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

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

class TTLFileConfigProviderConfig extends AbstractConfig {
    public static final String TTL_MS_CONFIG = "ttlfileconfig.ttl.ms";
    static final String TTL_MS_CONFIG_DOC = "The maximum amount of time the secrets read by the TTLFileConfigProvider are valid";

    public final long maxSecretTTL;

    public TTLFileConfigProviderConfig(Map<String, ?> settings) {
        super(config(), settings);

        this.maxSecretTTL = getLong(TTL_MS_CONFIG);
    }

    public static ConfigDef config() {
        return new ConfigDef()
            .define(TTL_MS_CONFIG,
                    ConfigDef.Type.LONG,
                    30000L,
                    ConfigDef.Range.atLeast(1000L),
                    ConfigDef.Importance.MEDIUM,
                    TTL_MS_CONFIG_DOC);

    }

}
