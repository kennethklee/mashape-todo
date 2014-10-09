package com.kenneth.todo.rest.resource;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import static org.junit.Assert.*;

import com.kenneth.todo.model.TaskModel;
import com.kenneth.todo.rest.resource.TaskResource;

public class TaskResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(TaskResource.class);
    }

    @Test
    public void testListEmpty() {
        final String json = target().path("/tasks").request().get(String.class);
        
        assertEquals("[]", json);
    }
    
    @Test
    public void testGetNotFound() {
        final Response response = target().path("/tasks/non-existant").request().get();
        
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
    
    @Test
    public void testCreateList() {
    	TaskModel model = new TaskModel(null, "test title", "test body", false);
    	
    	// Create
    	final Response response = target().path("/tasks").request().post(Entity.entity(model, MediaType.APPLICATION_JSON_TYPE));
    	
    	assertEquals(201, response.getStatus());
    }
}
