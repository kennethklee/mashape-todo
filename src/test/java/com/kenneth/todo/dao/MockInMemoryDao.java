package com.kenneth.todo.dao;

public class MockInMemoryDao extends GenericInMemoryDao<MockModel>{

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