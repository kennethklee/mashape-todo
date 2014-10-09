package com.kenneth.todo.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.kenneth.todo.dao.memory.GenericMemoryDao;

public class GenericInMemoryDaoTest {
	
	private GenericMemoryDao<MockModel> dao;
	
	@Before
	public void setUp() {
		this.dao = new MockInMemoryDao();
	}
	
	@Test
	public void testGet() {
		MockModel model = this.dao.get("fake");

		assertNotNull(model.getId());
	}
	
	@Test
	public void testGetNonExistant() {
		assertNull(dao.get("non-existant"));
	}

	@Test
	public void testCreate() {
		MockModel model = new MockModel();
		
		assertNull(model.getId());
		
		dao.create(model);
		assertNotNull(model.getId());
	}
	
	@Test
	public void testUpdate() {
		MockModel model = new MockModel();
		model.setId("fake");	// we want to update fake 
		model.setValue("changed");
		
		// Check before
		assertNotEquals(model.getValue(), this.dao.get("fake").getValue());
		
		this.dao.update(model);
		
		// Check after
		assertEquals(model.getValue(), this.dao.get("fake").getValue());
	}
	
	@Test
	public void testDelete() {
		assertNotNull(dao.get("fake"));
		
		this.dao.delete("fake");
		
		assertNull(dao.get("fake"));
	}
	
	@Test
	public void testFindAll() {
		List<MockModel> models = this.dao.findAll();
		assertEquals(1, models.size());
	}
	
	@Test
	public void testCountAll() {
		assertEquals(1, this.dao.countAll());
	}
}
