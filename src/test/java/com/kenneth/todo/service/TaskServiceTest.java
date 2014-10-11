package com.kenneth.todo.service;

import static org.junit.Assert.*;

import org.fluttercode.datafactory.impl.DataFactory;
import org.junit.Before;
import org.junit.Test;

import com.kenneth.todo.dao.mock.MockTaskMemoryDao;
import com.kenneth.todo.model.TaskModel;

public class TaskServiceTest {

	private DataFactory df;
	private TaskService taskService;
	
	public class MockSmsService implements SmsService {

		@Override
		public void sendComplete(TaskModel task) {
			assertEquals(true, task.isDone());
		}
		
	}
	
	@Before
	public void setup() {
		this.taskService = new TaskService(new MockTaskMemoryDao(), new MockSmsService());
		this.df = new DataFactory();
	}
	
	@Test
	public void testCompleteNotification() {
		TaskModel createdModel = this.taskService.create(new TaskModel("", "", "", 0, true));
		assertEquals(true, createdModel.isDone());
	}
	
	@Test
	public void testUpdate() {
		String updateId = "updateId";
		TaskModel model = new TaskModel(null, df.getRandomWord(), df.getRandomText(200), 0, true);
		
		this.taskService.update("updateId", model);
		
		assertEquals(updateId, model.getId());
	}
	
}
