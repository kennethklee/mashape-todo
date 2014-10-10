package com.kenneth.todo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kenneth.todo.dao.TaskDao;
import com.kenneth.todo.factory.SingletonFactory;
import com.kenneth.todo.model.TaskModel;

public class TaskService {
	private static Logger LOG = LoggerFactory.getLogger(TaskService.class);
	
	private TaskDao dao;
	private SmsService twilioService;
		
	public TaskService() {
		this.dao = SingletonFactory.getInstance().getTaskDao();
		this.twilioService = SingletonFactory.getInstance().getSmsService();
	}

	public List<TaskModel> list() {
		return this.dao.findAll();
	}

	public TaskModel create(TaskModel model) {
		this.dao.create(model);
		
		if (model.isDone()) {
			this.twilioService.sendComplete(model);
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
