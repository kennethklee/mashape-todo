package com.kenneth.todo.factory;

import java.io.IOException;
import java.util.Properties;

import com.kenneth.todo.dao.TaskDao;
import com.kenneth.todo.dao.memory.TaskMemoryDao;
import com.kenneth.todo.service.TaskService;

public class SingletonFactory {
	private static final String APPLICATION_PROPERTIES = "/application.properties";

	private Properties properties;
	private TaskDao taskDao;
	private TaskService taskService;

	private static class SingletonHolder {
		private static final SingletonFactory INSTANCE = new SingletonFactory();
	}

	private SingletonFactory() {
		properties = new Properties();

		try {
			properties.load(getClass().getResourceAsStream(APPLICATION_PROPERTIES));
		} catch (IOException e) {
			// TODO LOGGING!
			e.printStackTrace();
		}
	}

	public static SingletonFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public TaskDao getTaskDao() {
		if (this.taskDao == null) {
			synchronized (this) {
				if (this.taskDao == null) {
					this.taskDao = new TaskMemoryDao(null);
				}
			}
		}
		return this.taskDao;
	}

	public TaskService getTaskService() {
		if (this.taskService == null) {
			synchronized (this) {
				if (this.taskService == null) {
					this.taskService = new TaskService();
				}
			}
		}
		return this.taskService;
	}
}
