package com.kenneth.todo.dao;

public class MockInMemoryDao extends AbstractInMemoryDao<MockModel>{

	public MockInMemoryDao() {
		super();
		
		// Setup fake data
		MockModel fakeMockModel = new MockModel();
		fakeMockModel.setId("fake");
		fakeMockModel.setValue("fake");
		this.models.put(fakeMockModel.getId(), fakeMockModel);
	}
}