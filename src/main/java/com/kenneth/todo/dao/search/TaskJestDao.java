package com.kenneth.todo.dao.search;

import io.searchbox.client.JestClient;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult.Hit;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.IndicesExists;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kenneth.todo.dao.TaskDao;
import com.kenneth.todo.model.TaskModel;

/**
 * Implementation of a search task DAO using JestClient. Requires an innerDao for persistence.
 * storage.
 */
public class TaskJestDao implements TaskDao {

	private static final Logger LOG = LoggerFactory
			.getLogger(TaskJestDao.class);

	private static final String INDEX_NAME = "tasks";
	private static final String TYPE_NAME = "task";

	private TaskDao innerDao;
	private JestClient client;

	public TaskJestDao(JestClient client, TaskDao innerDao) {
		if (innerDao == null) {
			throw new IllegalArgumentException(
					"TaskSearchlyDao requires a non-null dao object.");
		}
		
		this.client = client;
		this.innerDao = innerDao;

		createIndexUnlessExists();
	}

	private void createIndexUnlessExists() {
		try {
			if (!indexExists()) {
				this.client
						.execute(new CreateIndex.Builder(INDEX_NAME).build());
			}
		} catch (Exception e) {
			LOG.warn("Failed to create index in searchly.", e);
		}
	}

	private boolean indexExists() throws Exception {
		IndicesExists indexExists = new IndicesExists.Builder("todo_app")
				.build();
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
			this.client.execute(new Index.Builder(createdModel)
					.index(INDEX_NAME).type(TYPE_NAME).build());
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
	public long countAll() {
		return innerDao.countAll();
	}

	@Override
	public void delete(String id) {
		innerDao.delete(id);

		deleteTaskFromIndex(id);
	}

	private void deleteTaskFromIndex(String id) {
		try {
			this.client.execute(new Delete.Builder(id).index(INDEX_NAME)
					.type(TYPE_NAME).build());
		} catch (Exception e) {
			LOG.error("Failed to delete task from index", e);
		}
	}

	@Override
	public List<TaskModel> findByQuery(String query) {
		List<TaskModel> results = new ArrayList<TaskModel>();
		
		QueryBuilder qb = QueryBuilders.multiMatchQuery(query, "title^3", "body");
		SearchSourceBuilder searchQuery = new SearchSourceBuilder().query(qb);

		Search search = new Search.Builder(searchQuery.toString())
			.addIndex(INDEX_NAME)
			.addType(TYPE_NAME)
			.build();
		
		try {
			List<Hit<TaskModel, Void>> hits = this.client.execute(search).getHits(TaskModel.class);

			for (Hit<TaskModel, Void> hit : hits) {
				results.add(hit.source);
			}
		} catch (Exception e) {
			LOG.error("Failed to search index", e);
		}
		
		return results;
	}
}
