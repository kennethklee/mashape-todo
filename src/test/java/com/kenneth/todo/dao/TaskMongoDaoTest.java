package com.kenneth.todo.dao;

import static org.junit.Assert.*;

import java.net.UnknownHostException;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.kenneth.todo.dao.mongo.TaskMongoDao;
import com.kenneth.todo.model.TaskModel;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * Not exactly a unit test. Made so I can test mongo. Real unit test would require mocking MongoClient.
 */
public class TaskMongoDaoTest {
	private static final String TEST_MONGO_URL = "mongodb://todo:test@ds035348.mongolab.com:35348/unit-test";
	private TaskMongoDao dao;
	
	@Before
	public void setUp() throws UnknownHostException {
		MongoClient mongoClient = new MongoClient(new MongoClientURI(TEST_MONGO_URL));
		this.dao = new TaskMongoDao(mongoClient, "unit-test");
	}
	
	@Test
	@Ignore
	public void testCreateAndGet() {
		TaskModel createdModel = dao.create(new TaskModel(null, "test", "test", 0, false));
		assertNotNull(createdModel.getId());
		assertEquals("test", createdModel.getTitle());
		
		List<TaskModel> models = dao.findAll();
		assertTrue(models.size() > 0);
	}
}
