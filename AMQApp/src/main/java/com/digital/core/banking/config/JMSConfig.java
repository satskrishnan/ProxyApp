package com.digital.core.banking.config;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JMSConfig {

	@Value("${spring.activemq.broker-url}")
	private String brokerUrl;

	@Value("${spring.activemq.user}")
	private String username;

	@Value("${spring.activemq.password}")
	private String password;

	@Value("${sender.queueName}")
	private String sQName;

	@Bean
	public Queue queue() {
		return new ActiveMQQueue(sQName);
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		return new ActiveMQConnectionFactory(username, password, brokerUrl);
	}

}
