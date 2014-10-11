package com.kenneth.todo.dao.memory;

import java.util.List;

import com.kenneth.todo.dao.TaskDao;
import com.kenneth.todo.model.TaskModel;

/**
 * This is an in-memory implementation of a {@link TaskDao}. Uses
 * {@link GenericMemoryDao} to store Tasks in memory.
 */
public class TaskMemoryDao extends GenericMemoryDao<TaskModel> implements
		TaskDao {

	private TaskDao innerDao;

	public TaskMemoryDao(TaskDao innerDao) {
		this.innerDao = innerDao;
	}

	@Override
	public List<TaskModel> findByQuery(String query) {
		if (innerDao != null) {
			return innerDao.findByQuery(query);
		}

		throw new UnsupportedOperationException();
	}

}
