package kafka.apache;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProducerApp {

    private static final Logger logger = LoggerFactory.getLogger(ProducerApp.class);

    private static final String KAFKA_PROPERTY_FILE = "kafka.properties";
    private static final  String KAFKA_PROPERTY_KEY = "demo.topic";

    private static Properties loadKafkaProperties() {
        Properties prop = new Properties();
        try (InputStream inputStream = ProducerApp.class.getClassLoader().getResourceAsStream(KAFKA_PROPERTY_FILE)) {
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
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
        String topic = properties.getProperty(KAFKA_PROPERTY_KEY);

        for (int i = 1; i <= 3; i++) {
            final String key = String.format("k%d", i);
            final String value = String.format("v%d", i);
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, value);
            logger.info("Sending message key: {}, value: {} ...", key, value);
            kafkaProducer.send(producerRecord, (md, ex) -> {
                if (ex != null) {
                    throw new RuntimeException(
                            String.format("Failed to send the message key: %s, value: %s", key, value), ex);
                }
                logger.info("Broker got the message in topic: {} with offset: {}, partition: {} ", md.topic(), md.partition(), md.offset());
            });
        }

        kafkaProducer.close();
    }
}
