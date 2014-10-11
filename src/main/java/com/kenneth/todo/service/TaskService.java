package com.kenneth.todo.service;

import java.util.List;

import com.kenneth.todo.dao.TaskDao;
import com.kenneth.todo.factory.SingletonFactory;
import com.kenneth.todo.model.TaskModel;

/**
 * Service to manage tasks.
 */
public class TaskService {
	private TaskDao dao;
	private SmsService smsService;
		
	public TaskService() {
		this.dao = SingletonFactory.getInstance().getTaskDao();
		this.smsService = SingletonFactory.getInstance().getSmsService();
	}

	public List<TaskModel> list() {
		return this.dao.findAll();
	}

	public TaskModel create(TaskModel model) {
		this.dao.create(model);
		
		if (model.isDone()) {
			this.smsService.sendComplete(model);
		}
		return model;
	}

	public TaskModel get(String id) {
		return this.dao.get(id);
	}

	public TaskModel update(String id, TaskModel model) {
    	model.setId(id);
		return dao.update(model);
	}

	public void delete(String id) {
		this.dao.delete(id);
	}
	
}
