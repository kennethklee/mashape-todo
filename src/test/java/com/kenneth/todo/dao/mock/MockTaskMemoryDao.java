package com.kenneth.todo.dao.mock;

import java.util.List;

import com.kenneth.todo.dao.TaskDao;
import com.kenneth.todo.dao.memory.GenericMemoryDao;
import com.kenneth.todo.model.TaskModel;

public class MockTaskMemoryDao extends GenericMemoryDao<TaskModel> implements TaskDao {

	@Override
	public List<TaskModel> findByQuery(String query) {
		return null;
	}

}
