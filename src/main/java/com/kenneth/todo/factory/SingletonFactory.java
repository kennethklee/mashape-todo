package com.kenneth.todo.factory;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kenneth.todo.dao.TaskDao;
import com.kenneth.todo.dao.memory.TaskMemoryDao;
import com.kenneth.todo.dao.mongo.TaskMongoDao;
import com.kenneth.todo.dao.search.TaskSearchlyDao;
import com.kenneth.todo.service.SmsService;
import com.kenneth.todo.service.TaskService;
import com.kenneth.todo.service.sms.DisabledSmsService;
import com.kenneth.todo.service.sms.TwilioSmsService;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.twilio.sdk.TwilioRestClient;

/**
 * Standard SingletonFactory design, because we're not going to use dependency injection for learning purposes. =)
 */
public class SingletonFactory {
	private static final String APPLICATION_PROPERTIES = "/application.properties";
	private static final Logger LOG = LoggerFactory.getLogger(SingletonFactory.class);

	private Properties properties;
	private TaskDao taskDao;
	private TaskService taskService;
	private SmsService smsService;
	private MongoClient mongoClient;
	private TwilioRestClient twilioClient;
	private JestClient jestClient;

	private static class SingletonHolder {
		private static final SingletonFactory INSTANCE = new SingletonFactory();
	}

	private SingletonFactory() {
		properties = new Properties();

		try {
			properties.load(getClass().getResourceAsStream(APPLICATION_PROPERTIES));
		} catch (IOException e) {
			LOG.error("Failed to load application properties.", e);
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
					TaskDao storageDao;

					String storage = this.properties.getProperty("data.storage");
					if (storage.equalsIgnoreCase("mongo")) {
						String name = this.properties.getProperty("mongo.name");
						storageDao = new TaskMongoDao(getMongoClient(), name);
					} else {
						storageDao = new TaskMemoryDao(null);
					}
					
					TaskDao searchDao = new TaskSearchlyDao(getJestClient(), storageDao);
					
					this.taskDao = searchDao;
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
					this.taskService = new TaskService(getTaskDao(), getSmsService());
				}
			}
		}
		return this.taskService;
	}
	
	/**
	 * @return an instance of an SMS Service
	 */
	public SmsService getSmsService() {
		if (this.smsService == null) {
			synchronized (this) {
				if (this.smsService == null) {
					String enabledString = this.properties.getProperty("sms.enabled");
					boolean enabled = Boolean.parseBoolean(enabledString);
					
					if (enabled) {
						String from = this.properties.getProperty("sms.from");
						String to = this.properties.getProperty("sms.to");
						
						this.smsService = new TwilioSmsService(getTwilioClient(), from, to);
					} else {
						this.smsService = new DisabledSmsService();
					}
				}
			}
		}
		return this.smsService;
	}

	public MongoClient getMongoClient() {
		if (this.mongoClient == null) {
			synchronized (this) {
				if (this.mongoClient == null) {
					String uri = this.properties.getProperty("mongo.uri");
					
					try {
						this.mongoClient = new MongoClient(new MongoClientURI(uri));
					} catch (UnknownHostException e) {
						LOG.error("Failed to connect to mongo database", e);
					}
				}
			}
		}

		return this.mongoClient;
	}

	public TwilioRestClient getTwilioClient() {
		if (this.twilioClient == null) {
			synchronized (this) {
				if (this.twilioClient == null) {
					String accountSid = this.properties.getProperty("twilio.sid");
					String authToken = this.properties.getProperty("twilio.token");

					this.twilioClient = new TwilioRestClient(accountSid, authToken);
				}
			}
		}
		return this.twilioClient;
	}

	public JestClient getJestClient() {
		if (this.taskService == null) {
			synchronized (this) {
				if (this.taskService == null) {
					String url = this.properties.getProperty("elasticsearch.url");
					
					JestClientFactory factory = new JestClientFactory();
					factory.setHttpClientConfig(new HttpClientConfig.Builder(url)
							.multiThreaded(true).build());
					this.jestClient = factory.getObject();
				}
			}
		}
		return this.jestClient;
	}
	

}
