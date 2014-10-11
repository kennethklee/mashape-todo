package com.kenneth.todo.dao.mongo;

import java.util.List;

import com.kenneth.todo.dao.TaskDao;
import com.kenneth.todo.model.TaskModel;
import com.mongodb.MongoClient;

public class TaskMongoDao implements TaskDao {

	private MongoClient mongoClient;
	
	public TaskMongoDao(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	@Override
	public TaskModel get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaskModel create(TaskModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaskModel update(TaskModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskModel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TaskModel delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskModel> findByQuery(String query) {
		// TODO Auto-generated method stub
		return null;
	}

}
