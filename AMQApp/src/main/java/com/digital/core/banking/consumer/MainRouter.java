package com.digital.core.banking.consumer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MainRouter extends RouteBuilder {

	private final Logger log = LoggerFactory.getLogger(MainRouter.class);

	@Value("${consumer.queueName}")
	private String sQName;

	@Value("${response.queueName}")
	private String fQName;

//	@Autowired
//	private ProducerTemplate producerTemplate;

	@Override
	public void configure() throws Exception {

//		// Consumer queue
//		from("activemq://" + sQName).process(new Processor() {
//			@Override
//			public void process(Exchange exchange) throws Exception {
//
//				String message = exchange.getIn().getBody(String.class);
//				log.info("--------------------------------");
//				log.info("Receive message '{}' from queue.", message);
//				producerTemplate.sendBody("activemq://" + fQName, message);
//				log.info("**********************************");
//			}
//		});

		from("activemq://" + sQName).log("Incoming Message: ${body}").process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {

				String message = exchange.getIn().getBody(String.class);
				log.info("--------------------------------");
				log.info("Receive message '{}' from queue.", message);
				log.info("**********************************");
			}
		}).multicast().parallelProcessing().to("activemq://" + fQName).bean(EmailService.class, "sendEmail(${body})")
				.end();
	}
}
