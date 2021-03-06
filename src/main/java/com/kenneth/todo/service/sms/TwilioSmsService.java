package com.kenneth.todo.service.sms;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kenneth.todo.model.TaskModel;
import com.kenneth.todo.service.SmsService;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;

/**
 * Single user Twilio SMS Service. Keeping it simple.
 */
public class TwilioSmsService implements SmsService {
	private static Logger LOG = LoggerFactory.getLogger(TwilioSmsService.class);
	
	private TwilioRestClient twilioClient;
	private String fromNumber;
	private String toNumber;

	/**
	 * Create a new Twilio service. All sms messages will come from the <i>fromNumber</i> parameter. Also,
	 * all message will be sent to the <i>toNumber</i>.
	 * @param fromNumber from sms number
	 * @param toNumber to this sms number
	 */
	public TwilioSmsService(TwilioRestClient twilioClient, String fromNumber, String toNumber) {
		this.twilioClient = twilioClient;
		this.fromNumber = fromNumber;
		this.toNumber = toNumber;
	}

	@Override
	public void sendTaskComplete(TaskModel task) {
		List<NameValuePair> messageParams = constructMessage("Completed Task, \"" + task.getTitle() + "\"!");
	 
	    MessageFactory messageFactory = this.twilioClient.getAccount().getMessageFactory();
	    
	    try {
			messageFactory.create(messageParams);
			LOG.info("Twilio: Sent Task Complete Message, " + task.getId());
		} catch (TwilioRestException e) {
			LOG.warn("Failed to send SMS message for task \"" + task.getId() + "\"", e);
		}
	}

	private List<NameValuePair> constructMessage(String body) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("Body", body));
	    params.add(new BasicNameValuePair("To", this.toNumber));
	    params.add(new BasicNameValuePair("From", this.fromNumber));
		return params;
	}
	
}
