package sarkkom.paragon.rmq.messaging.config;

import sarkkom.paragon.rmq.messaging.MQPublisher;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

@Configuration
@Profile("standalone")
public class LocalConfig {
	private static Logger logger = LoggerFactory.getLogger(LocalConfig.class);

	public static final String QUEUE1 = "queue1";
	public static final String QUEUE2 = "queue2";

	@Value("${rmq_host:localhost}")
	// @Value("${rmq_host:rabbitmq.local.pcfdev.io}")
	private String rmqHost;

	@Value("${rmq_port:15672}")
	private int rmqPort;

	@Value("${rmq_user:guest}")
	// @Value("${rmq_user:819c2710-06f7-403a-8efb-448713700b8d}")
	private String rmqUser;

	@Value("${rmq_password:guest}")
	// @Value("${rmq_password:9m81jdugap82kv3n7id2rbblgo}")
	private String rmqPassword;

	@Autowired
	ConnectionFactory connectionFactory;

	@Autowired
	RabbitTemplate rabbitTemplate;

	private RabbitAdmin rabbitAdmin;

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}


	@Bean
	public RabbitAdmin rabbitAdmin() {
		logger.info(" in rabbitAdmin bean setup");

		// idempotent operation to create queue
		// i.e. create a durable queue if it doesn't exist, else leave it alone.
		// the true flag drives durability.
		rabbitAdmin = new RabbitAdmin(connectionFactory);
		rabbitAdmin.declareQueue(new Queue(QUEUE1, true, false, false));
		logger.info("	... verified (if needed created) Queue '{}'", QUEUE1);
		rabbitAdmin.declareQueue(new Queue(QUEUE2, true, false, false));
		logger.info("	... verified (if needed created) Queue '{}'", QUEUE2);
		return rabbitAdmin;
	}

	@Bean
	public MQPublisher getPublisher() {
		Connection connection = connectionFactory.createConnection();
		Channel channel = connection.createChannel(false);

		MQPublisher publisher = new MQPublisher();
		// publisher.setChannel(channel);
		return publisher;
	}

	@Bean
	public CachingConnectionFactory localConnectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rmqHost);
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");
		return connectionFactory;
//
//		CachingConnectionFactory factory = new CachingConnectionFactory(rmqHost, rmqPort);
//		if (StringUtils.hasText(rmqUser)) connectionFactory.setUsername(rmqUser);
//		if (StringUtils.hasText(rmqPassword)) connectionFactory.setPassword(rmqPassword);
//		return connectionFactory;
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public MappingJackson2MessageConverter jackson2Converter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		return converter;
	}
}