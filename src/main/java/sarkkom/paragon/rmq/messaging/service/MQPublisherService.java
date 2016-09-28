package sarkkom.paragon.rmq.messaging.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sarkkom.paragon.rmq.messaging.MQPublisher;

@RestController
@RequestMapping("/publisher/{queueName}/messages")
public class MQPublisherService {

	private static Logger logger = LoggerFactory.getLogger(MQPublisherService.class);

	@Autowired
	private MQPublisher messagePublisher;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> publishMessages(@PathVariable String queueName, @RequestBody String message) {

		logger.info("Received request to publish to queue ({}) message ({})",queueName,message);

		try {
			messagePublisher.publishMessage(queueName,message);
		} catch (Exception e) {
			logger.error("Failed to publish to queue "+queueName, e);
			return new ResponseEntity<String>("Failed to publish to queue "+queueName, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return ResponseEntity.ok("");
	}

	public MQPublisher getMessagePublisher() {
		return messagePublisher;
	}
	public void setMessagePublisher(MQPublisher messagePublisher) {
		this.messagePublisher = messagePublisher;
	}
}
