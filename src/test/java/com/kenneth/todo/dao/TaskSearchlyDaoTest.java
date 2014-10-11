package com.kenneth.todo.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.fluttercode.datafactory.impl.DataFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.kenneth.todo.dao.mock.MockTaskMemoryDao;
import com.kenneth.todo.dao.search.TaskSearchlyDao;
import com.kenneth.todo.model.TaskModel;

/**
 * Not exactly a unit test. Needs mocking with mockito or jmock. But this is here for me to test searchly.
 */
public class TaskSearchlyDaoTest {
	
	private static final String TEST_SEARCHLY_URL = "https://site:9424ca85f81d41c3a7afb05426531330@bofur-us-east-1.searchly.com";
	private DataFactory df;
	private TaskSearchlyDao dao;
	
	@Before
	public void setUp() {
		this.df = new DataFactory();
		this.dao = new TaskSearchlyDao(new MockTaskMemoryDao(), TEST_SEARCHLY_URL);
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
		TaskModel createdModel = this.dao.create(new TaskModel("fake", title, df.getRandomWord(20), false));
		
		List<TaskModel> results = this.dao.findByQuery(title);
		assertTrue(results.size() > 0);
	}

}
