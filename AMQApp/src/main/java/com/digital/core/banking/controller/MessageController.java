package com.digital.core.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.jms.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class MessageController {

	@Autowired
	private Queue queue;

	@Autowired
	private JmsTemplate jmsTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

	@GetMapping("message/{message}")
	public ResponseEntity<String> publish(@PathVariable("message") final String message) {
		LOGGER.info("sending message='{}'", message);
		jmsTemplate.convertAndSend(queue, message);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@GetMapping("/hello")
	public Collection<String> sayHello() {
		return IntStream.range(0, 10).mapToObj(i -> "Hello number " + i).collect(Collectors.toList());
	}

}