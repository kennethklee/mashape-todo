package com.kenneth.todo.service.sms;

import com.kenneth.todo.model.TaskModel;
import com.kenneth.todo.service.SmsService;

/**
 * Sms Service that does absolutely nothing. Used when SMS is disabled.
 */
public class DisabledSmsService implements SmsService {

	@Override
	public void sendTaskComplete(TaskModel task) {
		// Do nothing
	}

}
