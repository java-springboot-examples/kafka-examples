package kafka.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProducerApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ProducerApp.class, args);

        MessageProducer producer = context.getBean(MessageProducer.class);

        /*
         * Sending a Hello World message to topic 'MyTopic'.
         * Must be received by both listeners with group foo
         * and bar with containerFactory fooKafkaListenerContainerFactory
         * and barKafkaListenerContainerFactory respectively.
         * It will also be received by the listener with
         * headersKafkaListenerContainerFactory as container factory.
         */
        producer.sendMessage("Hello, World!");

        /*
         * Sending message to a topic with 5 partitions,
         * each message to a different partition. But as per
         * listener configuration, only the messages from
         * partition 0 and 3 will be consumed.
         */
//        for (int i = 0; i < 5; i++) {
//            producer.sendMessageToPartition("Hello To Partitioned Topic!", i);
//        }
//        listener.partitionLatch.await(10, TimeUnit.SECONDS);
//
//        /*
//         * Sending message to 'filtered' topic. As per listener
//         * configuration,  all messages with char sequence
//         * 'World' will be discarded.
//         */
//        producer.sendMessageToFiltered("Hello MyTopic!");
//        producer.sendMessageToFiltered("Hello World!");
//        listener.filterLatch.await(10, TimeUnit.SECONDS);
//
//        /*
//         * Sending message to 'greeting' topic. This will send
//         * and received a java object with the help of
//         * greetingKafkaListenerContainerFactory.
//         */
//        producer.sendGreetingMessage(new Greeting("Greetings", "World!"));
//        listener.greetingLatch.await(10, TimeUnit.SECONDS);

        context.close();
    }
}
