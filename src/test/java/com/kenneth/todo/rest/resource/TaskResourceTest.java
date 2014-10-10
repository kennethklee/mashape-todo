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
    public void testUpdateNotFound() {
    	final TaskModel model = new TaskModel(null, df.getRandomWord(), df.getRandomText(200), false);
        final Response response = target().path("/tasks/non-existant").request().put(Entity.entity(model, MediaType.APPLICATION_JSON_TYPE));
        
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
    
    public void testDeleteNotFound() {
    	final TaskModel model = new TaskModel(null, df.getRandomWord(), df.getRandomText(200), false);
        final Response response = target().path("/tasks/non-existant").request().delete();
        
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
    
    @Test
    public void testCreateAndList() {
    	// Create
    	final TaskModel model = new TaskModel(null, df.getRandomWord(), df.getRandomText(200), false);
    	final Response response = target().path("/tasks").request().post(Entity.entity(model, MediaType.APPLICATION_JSON_TYPE));
    	
    	assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
    	// Can't seem to figure out how to get the response body from a response object.
   	
    	final String jsonList = target().path("/tasks").request().get(String.class);
    	assertTrue(jsonList.contains(model.getTitle()));
    	assertTrue(jsonList.contains(model.getBody()));
    }
    
    @Test
    public void testCreateAndUpdateAndDelete() {
    	// New model and the changed model
    	final TaskModel newModel = new TaskModel(null, df.getRandomWord(), df.getRandomText(200), false);
    	final TaskModel changedModel = new TaskModel(null, df.getRandomWord(), df.getRandomText(200), true);
    	assertNotEquals(newModel.getTitle(), changedModel.getTitle());
    	assertNotEquals(newModel.getBody(), changedModel.getBody());
    	assertNotEquals(newModel.isDone(), changedModel.isDone());
    	
    	// Create new model
    	final TaskModel createdModel = target().path("/tasks").request().post(Entity.entity(newModel, MediaType.APPLICATION_JSON_TYPE), TaskModel.class);
    	assertEquals(newModel.getTitle(), createdModel.getTitle());
    	assertEquals(newModel.getBody(), createdModel.getBody());
    	assertEquals(newModel.isDone(), createdModel.isDone());

    	// Get the id generated
    	final String generatedId = createdModel.getId();
    	
    	// Update with changed model
    	final TaskModel updatedModel = target().path("/tasks/" + generatedId).request().put(Entity.entity(changedModel, MediaType.APPLICATION_JSON_TYPE), TaskModel.class);
    	assertEquals(generatedId, updatedModel.getId());
    	assertEquals(changedModel.getTitle(), updatedModel.getTitle());
    	assertEquals(changedModel.getBody(), updatedModel.getBody());
    	assertEquals(changedModel.isDone(), updatedModel.isDone());
    	
    	final Response deleteResponse = target().path("/tasks/" + generatedId).request().delete();
    	assertEquals(Status.NO_CONTENT.getStatusCode(), deleteResponse.getStatus());
    }
}
