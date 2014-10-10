package com.kenneth.todo.factory;

import java.io.IOException;
import java.util.Properties;

import com.kenneth.todo.dao.TaskDao;
import com.kenneth.todo.dao.memory.TaskMemoryDao;
import com.kenneth.todo.service.TaskService;

/**
 * Standard SingletonFactory design, because we're not going to use dependency injection for learning purposes. =)
 */
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

	/**
	 * @return an instance of a SingletonFactory.
	 */
	public static SingletonFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * @return an instance of a TaskDao
	 */
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

	/**
	 * @return an instance of a TaskService.
	 */
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
