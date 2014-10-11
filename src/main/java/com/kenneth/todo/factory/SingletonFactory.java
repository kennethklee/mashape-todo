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
 * Standard SingletonFactory design, because we're not going to use dependency
 * injection for learning purposes. =)
 */
public class SingletonFactory {
	private static final String APPLICATION_PROPERTIES = "/application.properties";
	private static final Logger LOG = LoggerFactory
			.getLogger(SingletonFactory.class);

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
			properties.load(getClass().getResourceAsStream(
					APPLICATION_PROPERTIES));
		} catch (IOException e) {
			LOG.error("Failed to load application properties.", e);
		}
	}

	/**
	 * Retrieve a SingletonFactory. Loads up <i>application.properties</i> for configurations.
	 * @return an instance of a SingletonFactory.
	 */
	public static SingletonFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * Retrieve a TaskDao. Uses <i>data.storage</i> to determine the type of Dao to load up.
	 * If mongo, uses {@link MongoClient} singleton and <i>mongo.name</i> to create the MongoDao.
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
						storageDao = new TaskMongoDao(null, getMongoClient(), name);
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
	 * Retrieves a Task Service.
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
	 * Retrieves an SMS Service. Uses <i>sms.enabled</i> to determine whether to actually send SMS or not.
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

	/**
	 * Retrieve a mongo client. Uses <i>mongo.uri</i> for it's connection properties.
	 * @return an instance of a Mongo client
	 */
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

	/**
	 * Retrieve a TwilioRestClient. Uses <i>twilio.sid</i> and <i>twilio.token</i> to establish the connection.
	 * @return an instance of a twilio client
	 */
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

	/**
	 * Retrieve an elastic search JestClient. Uses <i>elasticsearch.url</i> to connect.
	 * @return an instance of jest client
	 */
	public JestClient getJestClient() {
		if (this.taskService == null) {
			synchronized (this) {
				if (this.taskService == null) {
					String url = this.properties.getProperty("elasticsearch.url");

					JestClientFactory factory = new JestClientFactory();
					HttpClientConfig clientConfig = new HttpClientConfig.Builder(url).multiThreaded(true).build();
					factory.setHttpClientConfig(clientConfig);
					this.jestClient = factory.getObject();
				}
			}
		}
		return this.jestClient;
	}

}
