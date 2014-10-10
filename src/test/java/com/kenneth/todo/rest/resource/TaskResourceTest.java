package com.kenneth.todo.rest.resource;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.fluttercode.datafactory.impl.DataFactory;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import static org.junit.Assert.*;

import com.kenneth.todo.model.TaskModel;
import com.kenneth.todo.rest.resource.TaskResource;

/**
 * Some integration testing on the resource
 */
public class TaskResourceTest extends JerseyTest {
	
	private DataFactory df = new DataFactory();
	
    @Override
    protected Application configure() {
        return new ResourceConfig(TaskResource.class)
        	.register(MoxyJsonFeature.class);
    }
    
    @Test
    public void testGetNotFound() {
        final Response response = target().path("/tasks/non-existant").request().get();
        
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
    
    @Test
    public void testCreateAndList() {
    	// Create
    	TaskModel model = new TaskModel(null, df.getRandomWord(), df.getRandomText(200), false);
    	final Response response = target().path("/tasks").request().post(Entity.entity(model, MediaType.APPLICATION_JSON_TYPE));
    	
    	assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
    	// Can't seem to figure out how to get the response body from a response object.
   	
    	final String jsonList = target().path("/tasks").request().get(String.class);
    	assertTrue(jsonList.contains(model.getTitle()));
    	assertTrue(jsonList.contains(model.getBody()));
    }
    
    @Test
    public void testCreateAndUpdate() {
    	// New model
    	TaskModel model = new TaskModel(null, df.getRandomWord(), df.getRandomText(200), false);
    	
    	// Create
    	final TaskModel createdTask = target().path("/tasks").request().post(Entity.entity(model, MediaType.APPLICATION_JSON_TYPE), TaskModel.class);
    	assertEquals(model.getTitle(), createdTask.getTitle());
    	assertEquals(model.getBody(), createdTask.getBody());
    	assertEquals(model.isDone(), createdTask.isDone());
    	
    	// Change model
    	model.setTitle(df.getRandomWord());	// Change title
    	model.setBody(df.getRandomText(200));	// Change body
    	model.setDone(true);	// Mark as complete
    	assertNotEquals(model.getTitle(), createdTask.getTitle());
    	assertNotEquals(model.getBody(), createdTask.getBody());
    	assertNotEquals(model.isDone(), createdTask.isDone());
    	
    	// Update
    	final TaskModel updateTask = target().path("/tasks/" + createdTask.getId()).request().put(Entity.entity(model, MediaType.APPLICATION_JSON_TYPE), TaskModel.class);
    	assertEquals(createdTask.getId(), updateTask.getId());
    	assertEquals(model.getTitle(), updateTask.getTitle());
    	assertEquals(model.getBody(), updateTask.getBody());
    	assertEquals(model.isDone(), updateTask.isDone());
    	assertNotEquals(createdTask.getTitle(), updateTask.getTitle());
    	assertNotEquals(createdTask.getBody(), updateTask.getBody());
    	assertNotEquals(createdTask.isDone(), updateTask.isDone());    	
    }
}
