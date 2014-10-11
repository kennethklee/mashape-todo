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
	private MockSmsService smsService;
	
	public class MockSmsService implements SmsService {
		
		public boolean calledTaskComplete = false;
		
		@Override
		public void sendTaskComplete(TaskModel task) {
			calledTaskComplete = true;
		}
		
	}
	
	@Before
	public void setup() {
		this.smsService = new MockSmsService();
		this.taskService = new TaskService(new MockTaskMemoryDao(), this.smsService);
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
		assertTrue(this.smsService.calledTaskComplete);
	}
	
	@Test
	public void testCreateDone() {
		TaskModel model = this.taskService.create(new TaskModel(null, df.getRandomWord(), df.getRandomText(200), 0, true)); 
		
		assertEquals(true, model.isDone());
		assertTrue(this.smsService.calledTaskComplete);
	}
	
}
