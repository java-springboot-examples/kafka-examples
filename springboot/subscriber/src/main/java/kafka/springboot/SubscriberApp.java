package kafka.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SubscriberApp {

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext context = SpringApplication.run(SubscriberApp.class, args);

		MessageListener listener = context.getBean(MessageListener.class);

		listener.await(60, TimeUnit.SECONDS);

		context.close();
	}

}
