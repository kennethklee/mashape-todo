package com.kenneth.todo.service;

import java.util.List;

import com.kenneth.todo.dao.TaskDao;
import com.kenneth.todo.factory.SingletonFactory;
import com.kenneth.todo.model.TaskModel;

public class TaskService {
	private TaskDao dao;
	
	public TaskService() {
		this.dao = SingletonFactory.getInstance().getTaskDao();;
	}

	public List<TaskModel> list() {
		return this.dao.findAll();
	}

	public TaskModel create(TaskModel model) {
		return this.dao.create(model);
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
