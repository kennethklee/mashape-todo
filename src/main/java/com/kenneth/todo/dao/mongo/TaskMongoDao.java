package com.kenneth.todo.dao.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.kenneth.todo.dao.TaskDao;
import com.kenneth.todo.model.TaskModel;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class TaskMongoDao implements TaskDao {

	private static final String COLLECTION_NAME = "tasks";
	private DBCollection collection;
	
	public TaskMongoDao(MongoClient mongoClient, String dbName) {
		this.collection = mongoClient.getDB(dbName).getCollection(COLLECTION_NAME);
	}

	@Override
	public TaskModel get(String id) {
		ObjectId objId;
		try {
			objId = new ObjectId(id);
		} catch (IllegalArgumentException e) {
			return null;
		}
		
		DBObject obj = this.collection.findOne(new BasicDBObject("_id", objId));
		
		return toModel(obj);
	}

	@Override
	public TaskModel create(TaskModel model) {
		DBObject obj = toDbObject(model);
		this.collection.insert(obj);
		
		model.setId(obj.get("_id").toString());
		return model;
	}

	@Override
	public TaskModel update(TaskModel model) {
		BasicDBObject query = new BasicDBObject("_id", new ObjectId(model.getId()));
		BasicDBObject updateObj = new BasicDBObject("$set", toDbObject(model));
		
		this.collection.update(query, updateObj);
		return model;
	}

	@Override
	public List<TaskModel> findAll() {
		List<TaskModel> results = new ArrayList<TaskModel>();
		
		DBCursor cursor = this.collection.find();
		for (DBObject dbObject : cursor) {
			results.add(toModel(dbObject));
		}
		return results;
	}

	@Override
	public long countAll() {
		return this.collection.count();
	}

	@Override
	public void delete(String id) {
		this.collection.remove(new BasicDBObject("_id", new ObjectId(id)));
	}

	@Override
	public List<TaskModel> findByQuery(String query) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private DBObject toDbObject(TaskModel model) {
		if (model == null) {
			return null;
		}
		
		DBObject obj = new BasicDBObject();
		obj.put("title", model.getTitle());
		obj.put("body", model.getBody());
		obj.put("done", model.isDone());
		
		return obj;
	}
	private TaskModel toModel(DBObject obj) {
		TaskModel model = new TaskModel();
		model.setId(obj.get("_id").toString());
		model.setTitle(obj.get("title").toString());
		model.setBody(obj.get("body").toString());
		model.setDone((boolean) obj.get("done"));
		
		return model;
	}
	
	//private List<TaskModel> toList()

}
