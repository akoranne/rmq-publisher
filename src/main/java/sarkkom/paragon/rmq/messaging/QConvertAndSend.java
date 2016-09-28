package sarkkom.paragon.rmq.messaging;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QConvertAndSend {

	@Autowired
	RabbitTemplate rabbitTemplate;

	private Channel channel;
	
	public void publishMessage(String queueName, Object payload) throws MessagePublisherException {
		rabbitTemplate.convertAndSend("", queueName, payload);
	}

	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
}
