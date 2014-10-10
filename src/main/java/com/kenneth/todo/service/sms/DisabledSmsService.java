package com.kenneth.todo.service.sms;

import com.kenneth.todo.model.TaskModel;
import com.kenneth.todo.service.SmsService;

public class DisabledSmsService implements SmsService {

	@Override
	public void sendComplete(TaskModel task) {
		// Do nothing
	}

}
