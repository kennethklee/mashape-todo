package com.kenneth.todo.service;

import com.kenneth.todo.model.TaskModel;

public interface SmsService {

	/**
	 * Send a task complete notification.
	 * @param title title of task
	 */
	public abstract void sendComplete(TaskModel task);

}