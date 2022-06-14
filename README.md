# TTLFileConfigProvier

A proof of concept Kafka Connect ConfigProvider that refreshes its data from a file based on a provided TTL. Based on the default Apache Kafka FileConfigProvider with some minor modifications.

The main changes compared to the default FileConfigProvider are:

- Added support for ConfigData TTL
- Added the necessary configuration logic so the TTL for the ConfigData is user configurable
- Created a standalone project with all required settings so Kafka Connect can load the plugin
