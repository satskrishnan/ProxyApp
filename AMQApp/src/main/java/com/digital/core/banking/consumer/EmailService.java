package com.digital.core.banking.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

	private static final Logger log = LoggerFactory.getLogger(EmailService.class);

	public void sendEmail(String message) {

		log.info("--------------------------------");
		log.info("Receive message in bean '{}' from queue.", message);
		log.info("**********************************");
	}
}
