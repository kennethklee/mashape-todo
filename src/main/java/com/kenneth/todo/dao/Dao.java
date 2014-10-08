package com.kenneth.todo.dao;

import java.util.List;

import com.kenneth.todo.model.Model;

/**
 * Base interface for a DAO
 *
 * @param <T> Model that the DAO interacts with
 */
public interface Dao<T extends Model> {

	public abstract T get(String id);

	public abstract T create(T model);

	public abstract T update(T model);

	public abstract List<T> findAll();

	public abstract T delete(T model);

}