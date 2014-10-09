package com.kenneth.todo.dao;

import java.util.List;

import com.kenneth.todo.model.TaskModel;

public interface TaskDao extends Dao<TaskModel> {
	/**
	 * Retrieve record by query, searching in title and body. Gives higher weight to title.
	 * @param query search term
	 * @return list of models that match the specified query
	 */
	public List<TaskModel> findByQuery(String query);

}