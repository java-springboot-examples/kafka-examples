# Kafka applications using apache client

## Producer

```shell
mvn exec:java -Dexec.mainClass=kafka.apache.ProducerApp
```

## Consumer

```shell script
mvn exec:java -Dexec.mainClass=kafka.apache.ConsumerApp -Dexec.args="group1"
mvn exec:java -Dexec.mainClass=kafka.apache.ConsumerApp -Dexec.args="group2"
```