package sarkkom.paragon.rmq.messaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MQPublisherServiceApplication {
	
	public MQPublisherServiceApplication() {
	}
	
	@java.lang.SuppressWarnings("squid:S2095")
	public static void main(String[] args) {
		
		SpringApplication.run(MQPublisherServiceApplication.class, args);
	}
}
