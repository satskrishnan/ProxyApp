package com.digital.core.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.jms.Queue;
import javax.validation.Valid;

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

	@PostMapping("/msg1")
	@ResponseBody
	public ResponseEntity<String> getMsg1(@Valid @RequestBody String msg) {
		return new ResponseEntity<String>("Thanks " + msg + "!!!", HttpStatus.OK);
	}

	@PostMapping("/msg2")
	@ResponseBody
	public ResponseEntity<String> getMsg2(@Valid @RequestBody String msg) {
		return new ResponseEntity<String>("Thank you " + msg + "!!!", HttpStatus.OK);
	}

	@PostMapping("/process")
	@ResponseBody
	public WebAsyncTask<String> sayHello(@Valid @RequestBody String name) {
		WebAsyncTask<String> task = new WebAsyncTask<String>(4000, () -> {
			LOGGER.info("task execution start...:" + name);
			Thread.sleep(3 * 1000);
			return "Welcome " + name + "!";
		});
		task.onTimeout(() -> {
			LOGGER.info("onTimeout...");
			return "Request timed out...";
		});
		task.onError(() -> {
			LOGGER.info("onError...");
			return "Some error occurred...";
		});
		task.onCompletion(() -> {
			LOGGER.info("task execution end...");
		});
		LOGGER.info("service end...");
		return task;
	}
}