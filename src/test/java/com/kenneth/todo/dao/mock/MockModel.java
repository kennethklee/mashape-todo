package com.kenneth.todo.dao.mock;

import io.searchbox.annotations.JestId;

import com.kenneth.todo.model.Model;

public class MockModel implements Model {

	@JestId
	private String id;
	private String value;
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id; 
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
