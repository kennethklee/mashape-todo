package com.kenneth.todo.dao;

import static org.junit.Assert.*;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

import java.util.List;

import org.fluttercode.datafactory.impl.DataFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.kenneth.todo.dao.mock.MockTaskMemoryDao;
import com.kenneth.todo.dao.search.TaskSearchlyDao;
import com.kenneth.todo.model.TaskModel;

/**
 * Not a unit test. Needs mocking with mockito or something. But this is here for me to test searchly.
 */
public class TaskSearchlyDaoTest {
	
	private static final String TEST_SEARCHLY_URL = "https://site:9424ca85f81d41c3a7afb05426531330@bofur-us-east-1.searchly.com";
	private DataFactory df;
	private TaskSearchlyDao dao;
	
	@Before
	public void setUp() {
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig.Builder(TEST_SEARCHLY_URL)
				.multiThreaded(true).build());

		this.df = new DataFactory();
		this.dao = new TaskSearchlyDao(factory.getObject(), new MockTaskMemoryDao());
	}
	
	@Test
	@Ignore
	public void testGet() {
		assertNull(this.dao.get("fake"));
	}
	
	@Test
	@Ignore
	public void testCreateAndFind() {
		String title = df.getRandomWord();
		this.dao.create(new TaskModel("fake", title, df.getRandomWord(20), false));
		
		List<TaskModel> results = this.dao.findByQuery(title);
		assertTrue(results.size() > 0);
	}

}
