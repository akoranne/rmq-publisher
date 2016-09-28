package sarkkom.paragon.rmq.messaging.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import sarkkom.paragon.rmq.messaging.MQPublisherServiceApplication;
import sarkkom.paragon.rmq.messaging.config.LocalConfig;
import com.google.common.io.CharStreams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertNotNull;

/**
 * Created by ajaykoranne on 6/9/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MQPublisherServiceApplication.class)
@ActiveProfiles("standalone")
public class MQPublisherTest {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private RabbitAdmin rabbitAdmin;


	@Test
	public void contextLoads() {
		assertNotNull(rabbitTemplate);
		assertNotNull(rabbitAdmin);
	}

//	public void check_for_queue() {
//		final String exchange = "foo";
//		boolean exists rabbitTemplate.execute(new ChannelCallback<DeclareOk>() {
//			@Override
//			public DeclareOk doInRabbit(Channel channel) throws Exception {
//				try {
//					return channel.exchangeDeclarePassive(exchange);
//				}
//				catch (Exception e) {
//					if (logger.isDebugEnabled()) {
//						logger.debug("Exchange '" + exchange + "' does not exist");
//					}
//					return null;
//				}
//			}
//		}) != null;
//	}

	@Test
	public void testPublishQueue1() throws Exception {

		InputStream inputStream = this.getClass().getResourceAsStream("/sample_shipment.json");
		String json = CharStreams.toString(new InputStreamReader(inputStream, "UTF-8"));
		ObjectMapper mapper = new ObjectMapper();
		String shipment = mapper.readValue(json, String.class);

		for (int cnt = 0; cnt < 1; cnt++) {
			StringBuilder msgTxt = new StringBuilder();
			msgTxt.append(" Message from [");
			msgTxt.append(this.getClass().getSimpleName());
			msgTxt.append(" ] #: ").append(cnt);

			rabbitTemplate.convertAndSend("", LocalConfig.QUEUE1, shipment);
			if (cnt % 10 == 0) {
				System.out.println("	... posted message -  " + cnt);
			}
		}
	}

	@Test
	public void testPublishQueue2() throws Exception {
		InputStream inputStream = this.getClass().getResourceAsStream("/sample_shipmentStatus.json");
		String json = CharStreams.toString(new InputStreamReader(inputStream, "UTF-8"));
		ObjectMapper mapper = new ObjectMapper();
		String status = mapper.readValue(json, String.class);

		for (int cnt = 0; cnt < 100; cnt++) {
			StringBuilder msgTxt = new StringBuilder();
			msgTxt.append(" Message from [");
			msgTxt.append(this.getClass().getSimpleName());
			msgTxt.append(" ] #: ").append(cnt);

			rabbitTemplate.convertAndSend("", LocalConfig.QUEUE2, status);
			if (cnt % 10 == 0) {
				System.out.println("	... posted message -  " + cnt);
			}
		}
	}


	@Test
	public void testPublishQueue1_invalid() throws Exception {

		InputStream inputStream = this.getClass().getResourceAsStream("/sample_shipment.json");
		String json = CharStreams.toString(new InputStreamReader(inputStream, "UTF-8"));

		for (int cnt = 0; cnt < 1; cnt++) {
			StringBuilder msgTxt = new StringBuilder();
			msgTxt.append(" Message from [");
			msgTxt.append(this.getClass().getSimpleName());
			msgTxt.append(" ] #: ").append(cnt);

			rabbitTemplate.convertAndSend("", LocalConfig.QUEUE1, json);
			if (cnt % 10 == 0) {
				System.out.println("	... posted message -  " + cnt);
			}
		}
	}

}
