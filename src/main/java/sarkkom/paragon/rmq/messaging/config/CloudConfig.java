package sarkkom.paragon.rmq.messaging.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import sarkkom.paragon.rmq.messaging.MQPublisher;
import com.rabbitmq.client.Channel;

@Configuration
@Profile("cloud")
public class CloudConfig {

	private static Logger logger = LoggerFactory.getLogger(CloudConfig.class);

	@Autowired 
	private ConnectionFactory conFactory;

	@Bean
	public MQPublisher getPublisher() {
		Connection connection = conFactory.createConnection();
	    Channel channel = connection.createChannel(false);
	    
	    MQPublisher publisher = new MQPublisher();
	    publisher.setChannel(channel);
	    return publisher;
	}

}