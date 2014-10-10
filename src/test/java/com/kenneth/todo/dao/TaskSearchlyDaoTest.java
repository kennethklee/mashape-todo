package com.kenneth.todo.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.kenneth.todo.dao.mock.MockTaskMemoryDao;
import com.kenneth.todo.dao.search.TaskSearchlyDao;

/**
 * Not exactly a unit test. Necessary to make sure the searchly module works. 
 */
public class TaskSearchlyDaoTest {
	
	private static final String TEST_SEARCHLY_URL = "https://site:9424ca85f81d41c3a7afb05426531330@bofur-us-east-1.searchly.com";
	private TaskSearchlyDao dao;
	
	@Before
	public void setUp() {
		this.dao = new TaskSearchlyDao(new MockTaskMemoryDao(), TEST_SEARCHLY_URL);
	}
	
	@Test
	public void testGet() {
		assertNull(this.dao.get("fake"));
	}
}
