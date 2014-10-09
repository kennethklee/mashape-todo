package com.kenneth.todo.dao;

import com.kenneth.todo.dao.memory.GenericMemoryDao;

public class MockInMemoryDao extends GenericMemoryDao<MockModel>{

	public MockInMemoryDao() {
		super();
		
		// Setup fake data
		MockModel fakeMockModel = new MockModel();
		fakeMockModel.setId("fake");
		fakeMockModel.setValue("fake");
		
		// Put into database
		this.models.put(fakeMockModel.getId(), fakeMockModel);
	}
}