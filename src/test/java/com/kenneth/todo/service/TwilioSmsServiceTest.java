package com.kenneth.todo.service;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.kenneth.todo.model.TaskModel;
import com.kenneth.todo.service.sms.TwilioSmsService;
import com.twilio.sdk.TwilioRestClient;

/**
 * Not really a unit test. Needs feedback from service and mocking the twilio client. 
 */
public class TwilioSmsServiceTest {

	private static final String TEST_TOKEN = "865ee45c76a56c09c9608f5c8e2e26d7";
	private static final String TEST_SID = "AC81bc5de968264040bb9273f1fbc185a5";
	
	private TwilioSmsService smsService;
	
	@Before
	public void setUp() {
		String accountSid = TEST_SID;
		String authToken = TEST_TOKEN;

		TwilioRestClient twilioClient = new TwilioRestClient(accountSid, authToken);
		this.smsService = new TwilioSmsService(twilioClient, "+14378001919", "+16474640270");
	}
	
	@Test
	@Ignore
	public void testSendCompetion() {
		smsService.sendTaskComplete(new TaskModel("id", "title", "body", 0, true));
	}
}
