package sarkkom.paragon.rmq.messaging.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class MQPublisherClient {

	private static final Logger logger = LoggerFactory.getLogger(MQPublisherClient.class);
	private RestTemplate restTemplate; 
	private String uriBase; 
	
	public MQPublisherClient() {
		restTemplate = new RestTemplate();
		uriBase = "http://localhost:8080";
	}
	
	public void sendMessage(String queue, String message) {
		restTemplate.postForObject(uriBase+"/publisher/"+queue+"/messages", message, Void.class);
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	public String getUriBase() {
		return uriBase;
	}
	public void setUriBase(String uriBase) {
		this.uriBase = uriBase;
	}
}
