package com.kenneth.todo.dao;

import java.util.List;

import com.kenneth.todo.model.TaskModel;

/**
 * Base interface for a Task Data Access Object (DAO). This is used to interact with Tasks and storage/queries.
 * 
 * <b>NOTE:</b> throws {@link UnsupportedOperationException} when the method isn't implemented.
 */
public interface TaskDao extends Dao<TaskModel> {
	/**
	 * Retrieve record by query, searching in title and body. Gives higher weight to title.
	 * @param query search term
	 * @return list of models that match the specified query
	 */
	public List<TaskModel> findByQuery(String query);

}