package com.digital.core.banking.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

@Component
public class RestService {

	private static final Logger log = LoggerFactory.getLogger(RestService.class);

	public void sendResponse(String message) {

		log.info("******* REST SERVICE Start *****************");
		log.info("Message:" + message);
		HttpResponse<String> response = Unirest.post("http://localhost:8080/amqApp/api/process")
				.header("Content-Type", "application/json").body(message).asString();

		log.info(response.getBody());
		log.info(response.getStatusText());
		log.info("Status:" + response.getStatus());
		log.info("******* REST SERVICE End*****************");

	}

}
