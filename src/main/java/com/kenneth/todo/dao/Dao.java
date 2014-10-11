package com.kenneth.todo.dao;

import java.util.List;

import com.kenneth.todo.model.Model;

/**
 * Base interface for a Data Access Object (DAO).
 *
 * @param <T> Model that the DAO interacts with
 */
public interface Dao<T extends Model> {

	/**
	 * Retrieve record by id.
	 * @param id key to retrieve by
	 * @return model
	 */
	public T get(String id);

	/**
	 * Create a new record. Also generates a new id for the model.
	 * @param model the model to create
	 * @return created model
	 */
	public T create(T model);

	/**
	 * Updates an existing record using the id of the model.
	 * @param model the model to update to
	 * @return updated model
	 */
	public T update(T model);

	/**
	 * Find all records
	 * @return list of models
	 */
	public List<T> findAll();
	
	/**
	 * Retrieve a count of all model records in database.
	 * @return count
	 */
	public long countAll();

	/**
	 * Deletes the model by id from the database.
	 * @param id identifier of model to delete
	 */
	public void delete(String id);

}