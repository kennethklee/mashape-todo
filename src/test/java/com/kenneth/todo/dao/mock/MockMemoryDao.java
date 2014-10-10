package com.kenneth.todo.dao.mock;

import com.kenneth.todo.dao.memory.GenericMemoryDao;

public class MockMemoryDao extends GenericMemoryDao<MockModel>{

	public MockMemoryDao() {
		super();
		
		// Setup fake data
		MockModel fakeMockModel = new MockModel();
		fakeMockModel.setId("fake");
		fakeMockModel.setValue("fake");
		
		// Put into database
		this.models.put(fakeMockModel.getId(), fakeMockModel);
	}
}