package com.kenneth.todo.dao.search;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.IndicesExists;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kenneth.todo.dao.TaskDao;
import com.kenneth.todo.model.TaskModel;

/**
 * Implementation of a Searchly Task Dao. Requires an innerDao for persistent storage.
 */
public class TaskSearchlyDao implements TaskDao {

	private static final String INDEX_NAME = "tasks";
	private static final String TYPE_NAME = "task";
	private static final Logger LOG = LoggerFactory.getLogger(TaskSearchlyDao.class);
	private TaskDao innerDao;
	private JestClient client;
	
	public TaskSearchlyDao(TaskDao innerDao, String url) {
		if (innerDao == null) {
			throw new IllegalArgumentException("SearchlyDao requires an inner dao object.");
		}
		
		this.innerDao = innerDao;
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig.Builder(url).multiThreaded(true).build());
		this.client = factory.getObject();
		
		createIndexUnlessExists();
	}

	private void createIndexUnlessExists() {
		try {
			if (!indexExists()) {
				this.client.execute(new CreateIndex.Builder(INDEX_NAME).build());
			}
		} catch (Exception e) {
			LOG.warn("Failed to create index in searchly.", e);
		}
	}

	private boolean indexExists() throws Exception {
		IndicesExists indexExists = new IndicesExists.Builder("todo_app").build();
		return this.client.execute(indexExists).isSucceeded();
	}

	@Override
	public TaskModel get(String id) {
		return innerDao.get(id);
	}

	@Override
	public TaskModel create(TaskModel model) {
		TaskModel createdModel = innerDao.create(model);
		createTaskInIndex(createdModel);
		
		return createdModel;
	}

	private void createTaskInIndex(TaskModel createdModel) {
		try {
			this.client.execute(new Index.Builder(createdModel).index(INDEX_NAME).type(TYPE_NAME).build());
		} catch (Exception e) {
			LOG.warn("Failed to index task.", e);
		}
	}

	@Override
	public TaskModel update(TaskModel model) {
		TaskModel updatedModel = innerDao.update(model);
		
		deleteTaskFromIndex(updatedModel.getId());
		createTaskInIndex(updatedModel);
		
		return updatedModel;
	}

	@Override
	public List<TaskModel> findAll() {
		return innerDao.findAll();
	}

	@Override
	public int countAll() {
		return innerDao.countAll();
	}

	@Override
	public TaskModel delete(String id) {
		TaskModel deleteModel = innerDao.delete(id);
		
		deleteTaskFromIndex(id);
		
		return deleteModel;
	}

	private void deleteTaskFromIndex(String id) {
		try {
			this.client.execute(new Delete.Builder(id).index(INDEX_NAME).type(TYPE_NAME).build());
		} catch (Exception e) {
			LOG.error("Failed to delete task from index", e);
		}
	}

	@Override
	public List<TaskModel> findByQuery(String query) {
		return innerDao.findByQuery(query);
	}

}
