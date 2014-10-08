package com.kenneth.todo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.kenneth.todo.model.Model;

/**
 * Generic DAO stored in-memory
 * 
 * @param <T>
 */
public abstract class AbstractInMemoryDao <T extends Model> implements Dao<T> {

	// Our in-memory database
	protected Map<String, T> models;
	
	public AbstractInMemoryDao() {
		this.models = new HashMap<String, T>();
	}

	/* (non-Javadoc)
	 * @see com.kenneth.todo.dao.Dao#get(java.lang.String)
	 */
	@Override
	public T get(String id) {
		return this.models.get(id);
	}
	
	/* (non-Javadoc)
	 * @see com.kenneth.todo.dao.Dao#create(T)
	 */
	@Override
	public T create(T model) {
		model.setId(UUID.randomUUID().toString());
		return this.models.put(model.getId(), model);
	}
	
	/* (non-Javadoc)
	 * @see com.kenneth.todo.dao.Dao#update(T)
	 */
	@Override
	public T update(T model) {
		return this.models.put(model.getId(), model);
	}
	
	/* (non-Javadoc)
	 * @see com.kenneth.todo.dao.Dao#findAll()
	 */
	@Override
	public List<T> findAll() {
		return (List<T>) this.models.values();
	}
	
	/* (non-Javadoc)
	 * @see com.kenneth.todo.dao.Dao#delete(java.lang.String)
	 */
	@Override
	public T delete(String id) {
		return this.models.remove(id);
	}
}
