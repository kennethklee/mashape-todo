package com.kenneth.todo.model;

public class TaskModel implements Model {

	private String id;
	private String title;
	private String body;
	private boolean done;
		
	public TaskModel() {
	}
	
	public TaskModel(String id, String title, String body, boolean done) {
		this.id = id;
		this.title = title;
		this.body = body;
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

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
	
}
