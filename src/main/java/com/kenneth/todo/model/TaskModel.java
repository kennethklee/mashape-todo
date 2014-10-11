package com.kenneth.todo.model;

import io.searchbox.annotations.JestId;

/**
 * Model that represents a task in a todo list.
 * 
 * NOTE: Should include active, and timestamps for record tracking and auditing. Since this is a simple example, we won't add them.
 */
public class TaskModel implements Model {

	@JestId
	private String id;
	private String title;
	private String body;
	private int order;
	private boolean done;
		
	/**
	 * Create an empty TaskModel. Use the setters to fill out the data.
	 */
	public TaskModel() {
		this.order = 1;
	}
	
	/**
	 * Construct a TaskModel with specific data.
	 * @param id model identifier
	 * @param title title of task
	 * @param body description of task
	 * @param done whether the task is complete or not
	 */
	public TaskModel(String id, String title, String body, int order, boolean done) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.order = order;
		this.done = done;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
	
}
