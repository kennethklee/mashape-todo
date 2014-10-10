package com.kenneth.todo.dao.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.kenneth.todo.dao.Dao;
import com.kenneth.todo.model.Model;

/**
 * Generic DAO stored in-memory
 * 
 * @param <T> Model to use for storage
 */
public class GenericMemoryDao<T extends Model> implements Dao<T> {

	// Our in-memory database
	protected Map<String, T> models;

	/**
	 * Create a new instance of an in-memory database.
	 */
	public GenericMemoryDao() {
		this.models = new HashMap<String, T>();
	}

	@Override
	public T get(String id) {
		return this.models.get(id);
	}

	@Override
	public T create(T model) {
		model.setId(UUID.randomUUID().toString());
		this.models.put(model.getId(), model);
		return model;
	}

	@Override
	public T update(T model) {
		this.models.put(model.getId(), model);
		return model;
	}

	@Override
	public List<T> findAll() {
		return new ArrayList<T>(this.models.values());
	}

	@Override
	public int countAll() {
		return this.models.size();
	}

	@Override
	public T delete(String id) {
		return this.models.remove(id);
	}

}
