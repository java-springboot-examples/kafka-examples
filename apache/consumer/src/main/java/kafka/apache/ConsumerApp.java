package kafka.apache;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerApp {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerApp.class);

    private static final String KAFKA_PROPERTY_FILE = "kafka.properties";
    private static final String KAFKA_PROPERTY_KEY = "demo.topic";

    private static Properties loadKafkaProperties() {
        Properties prop = new Properties();
        try (InputStream inputStream = ConsumerApp.class.getClassLoader().getResourceAsStream(KAFKA_PROPERTY_FILE)) {
            prop.load(inputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("Kafka property file %s not found!", KAFKA_PROPERTY_FILE), e);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Failed to load Kafka property file: %s", KAFKA_PROPERTY_FILE), e);
        }
        return prop;
    }

    public static void main(String[] args) {
        Properties properties = loadKafkaProperties();
        if (args.length > 0) {
            logger.info("Run consumer app using consumer group: {}", args[0]);
            properties.put(ConsumerConfig.GROUP_ID_CONFIG, args[0]);

            try (KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties)) {

                logger.info("Retrieving existing topics ...");
                kafkaConsumer.listTopics().forEach(
                        (topic, partitionInfoList) -> partitionInfoList.forEach(
                                partitionInfo -> logger.info("Topic: {} -> partitionInfo: {}", topic, partitionInfo)));
                String topic = properties.getProperty(KAFKA_PROPERTY_KEY);
                logger.info("Subscribing to topic: {}", topic);
                kafkaConsumer.subscribe(Collections.singletonList(topic));

                kafkaConsumer.poll(Duration.ofSeconds(60))
                        .forEach(record -> logger.info("Message received: key: {}, value: {}, partition: {}, offset: {}"
                                , record.key(), record.value(), record.partition(), record.offset()));
                kafkaConsumer.commitAsync();
            } finally {
                logger.info("Closing Kafka consumer ...");
            }
        } else {
            logger.error("Use group id as the argument.");
        }
    }
}
