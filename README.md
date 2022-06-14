# TTLFileConfigProvier

A proof of concept Kafka Connect ConfigProvider that refreshes its data from a file based on a provided TTL. Based on the default Apache Kafka [FileConfigProvider](https://github.com/apache/kafka/blob/trunk/clients/src/main/java/org/apache/kafka/common/config/provider/FileConfigProvider.java) with some minor modifications.

The main changes compared to the default FileConfigProvider are:

- Added support for ConfigData TTL
- Added the necessary configuration logic so the TTL for the ConfigData is user configurable
- Created a standalone project with all required settings so Kafka Connect can load the plugin


## Usage

Setting up the TTLFileConfigProvider is very similar to setting up the regular FileConfigProvider. You have to set up a ConfigProvider type in the connect worker properties file and reference the TTLFileConfigProvider main class and configure the lifetime for the ConfigData information by setting the `ttlfileconfig.ttl.ms` property to an appropriate value in milliseconds:

```
config.providers=file
config.providers.file.class=com.lonecppcoder.kafka.config.TTLFileConfigProvider
config.providers.file.param.ttlfileconfig.ttl.ms=45000
```

Please note that if you don't provide a `ttlfileconfig.ttl.ms` setting, it will default to 30000ms (30s). Please note that you can't disable the TTL mechanism, if you need a file based ConfigProvider with 'infinite' TTL for the ConfigData, please use the default FileConfigProvider instead.
