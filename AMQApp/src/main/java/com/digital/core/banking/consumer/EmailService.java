package com.digital.core.banking.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;

@Component
public class EmailService {

	private static final Logger log = LoggerFactory.getLogger(EmailService.class);

	public void sendEmail(String message) {

		log.info("--------------------------------");

		Email from = new Email("admin@finbank.com");
		String subject = "Event Processed.";
		Email to = new Email("satishkumar.pk@gmail.com");
		Content content = new Content("text/plain", message);
		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid(System.getProperty("SENDGRID_API_KEY"));
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			log.info(response.toString());
		} catch (IOException ex) {
			log.error("Error in sending email:" + ex.getMessage());
		}

		log.info("**********************************");
	}
}
