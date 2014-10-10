package com.kenneth.todo.service;

import static org.junit.Assert.*;

import org.fluttercode.datafactory.impl.DataFactory;
import org.junit.Before;
import org.junit.Test;

import com.kenneth.todo.model.TaskModel;

public class TaskServiceTest {

	private DataFactory df;
	private TaskService taskService;
	
	@Before
	public void setup() {
		this.taskService = new TaskService();
		this.df = new DataFactory();
	}
	
	@Test
	public void testUpdate() {
		String updateId = "updateId";
		TaskModel model = new TaskModel(null, df.getRandomWord(), df.getRandomText(200), true);
		
		this.taskService.update("updateId", model);
		
		assertEquals(updateId, model.getId());
	}
	
}
