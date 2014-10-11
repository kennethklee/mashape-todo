package com.kenneth.todo.rest.resource;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.fluttercode.datafactory.impl.DataFactory;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Ignore;
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
    	final TaskModel model = new TaskModel(null, df.getRandomWord(), df.getRandomText(200), 0, false);
        final Response response = target().path("/tasks/non-existant").request().put(Entity.entity(model, MediaType.APPLICATION_JSON_TYPE));
        
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
    
    public void testDeleteNotFound() {
    	final Response response = target().path("/tasks/non-existant").request().delete();
        
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
    
    @Test
    public void testCreateAndList() {
    	// Create
    	final TaskModel model = new TaskModel(null, df.getRandomWord(), df.getRandomText(200), 0, false);
    	final Response response = target().path("/tasks").request().post(Entity.entity(model, MediaType.APPLICATION_JSON_TYPE));
    	
    	assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
    	TaskModel createdModel = response.readEntity(TaskModel.class);
    	assertEquals(model.getTitle(), createdModel.getTitle());
    	assertEquals(model.getBody(), createdModel.getBody());
    	assertEquals(model.isDone(), createdModel.isDone());
   	
    	// Ensure it's in there
    	final String jsonList = target().path("/tasks").request().get(String.class);
    	assertTrue(jsonList.contains(model.getTitle()));
    	assertTrue(jsonList.contains(model.getBody()));
    	
    	// Delete (clean up a bit)
    	final Response deleteResponse = target().path("/tasks/" + createdModel.getId()).request().delete();
    	assertEquals(Status.NO_CONTENT.getStatusCode(), deleteResponse.getStatus());
    }
    
    @Test
    public void testCreateAndUpdateAndDelete() {
    	// New model and the changed model
    	final TaskModel newModel = new TaskModel(null, df.getRandomWord(), df.getRandomText(200), 0, false);
    	final TaskModel changedModel = new TaskModel(null, df.getRandomWord(), df.getRandomText(200), 0, true);
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
    	
    	// Delete
    	final Response deleteResponse = target().path("/tasks/" + generatedId).request().delete();
    	assertEquals(Status.NO_CONTENT.getStatusCode(), deleteResponse.getStatus());
    }
    
    @Test
    @Ignore("Needs mockito to mock out the external request.")
    public void testSearch() {
    	// Create
    	final TaskModel newModel = new TaskModel(null, df.getRandomWord(), df.getRandomText(200), 0, false);
    	final TaskModel createdModel = target().path("/tasks").request().post(Entity.entity(newModel, MediaType.APPLICATION_JSON_TYPE), TaskModel.class);
    	assertEquals(newModel.getTitle(), createdModel.getTitle());
    	assertEquals(newModel.getBody(), createdModel.getBody());
    	assertEquals(newModel.isDone(), createdModel.isDone());
    	
    	// Search for created task
    	final Response response = target().path("/tasks").queryParam("q", newModel.getTitle()).request().get();
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        List<TaskModel> searchResults = response.readEntity(new GenericType<List<TaskModel>>() {});
        assertTrue(searchResults.size() > 0);	// There's at least one result

    	// Delete
    	final Response deleteResponse = target().path("/tasks/" + createdModel.getId()).request().delete();
    	assertEquals(Status.NO_CONTENT.getStatusCode(), deleteResponse.getStatus());

    	// Search for deleted task
    	final Response deletedResponse = target().path("/tasks").queryParam("q", newModel.getTitle()).request().get();
        assertEquals(Status.OK.getStatusCode(), deletedResponse.getStatus());
        List<TaskModel> deletedSearchResults = deletedResponse.readEntity(new GenericType<List<TaskModel>>() {});
        assertEquals(0, deletedSearchResults.size());	// All gone!

    }
}
