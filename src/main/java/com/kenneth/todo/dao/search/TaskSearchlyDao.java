package com.kenneth.todo.dao.search;

import java.util.List;

import com.kenneth.todo.dao.TaskDao;
import com.kenneth.todo.model.TaskModel;

public class TaskSearchlyDao implements TaskDao {

	private TaskDao innerDao;
	private String apiKey;
	
	public TaskSearchlyDao(TaskDao innerDao, String apiKey) {
		this.innerDao = innerDao;
		this.apiKey = apiKey;
	}

	@Override
	public TaskModel get(String id) {
		if (innerDao != null) {
			return innerDao.get(id);
		}
		
		throw new UnsupportedOperationException();
	}

	@Override
	public TaskModel create(TaskModel model) {
		if (innerDao != null) {
			return innerDao.create(model);
		}
		
		throw new UnsupportedOperationException();
	}

	@Override
	public TaskModel update(TaskModel model) {
		if (innerDao != null) {
			return innerDao.update(model);
		}
		
		throw new UnsupportedOperationException();
	}

	@Override
	public List<TaskModel> findAll() {
		if (innerDao != null) {
			return innerDao.findAll();
		}
		
		throw new UnsupportedOperationException();
	}

	@Override
	public int countAll() {
		if (innerDao != null) {
			return innerDao.countAll();
		}
		
		throw new UnsupportedOperationException();
	}

	@Override
	public TaskModel delete(String id) {
		if (innerDao != null) {
			return innerDao.delete(id);
		}
		
		throw new UnsupportedOperationException();
	}

	@Override
	public List<TaskModel> findByQuery(String query) {
		if (innerDao != null) {
			return innerDao.findByQuery(query);
		}
		
		throw new UnsupportedOperationException();
	}

}
