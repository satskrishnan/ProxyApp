package com.digital.core.banking.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.EnableJms;
//import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@EnableJms
@Component
public class MessageConsumer {

	private final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

	// @JmsListener(destination = "${consumer.queueName}")
	public void listener(String message) {
		logger.info("Message received {} ", message);
	}
}