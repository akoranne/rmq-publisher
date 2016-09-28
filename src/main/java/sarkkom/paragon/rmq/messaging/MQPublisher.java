package sarkkom.paragon.rmq.messaging;

import java.io.IOException;

import com.rabbitmq.client.Channel;

public class MQPublisher {

	private Channel channel;
	
	public void publishMessage(String queueName, String message) throws MessagePublisherException {
	    try {
	    	channel.queueDeclare(queueName, false, false, false, null);
			channel.basicPublish("", queueName, null, message.getBytes());
		} catch (IOException e) {
			throw new MessagePublisherException(e.getMessage(),e);
		}
	}

	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
}
