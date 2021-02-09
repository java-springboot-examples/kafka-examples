# spring-kafka-examples
Basic Kafka functions examples using spring


## Create topics in kafka

The following topics must be created manually as Kafka topics are not created automatically by default.

```shell script
kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic MyTopic
kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 5 --topic partitioned
kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic filtered
kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic greeting
```