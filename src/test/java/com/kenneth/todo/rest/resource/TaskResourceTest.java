package com.kenneth.todo.rest.resource;

import javax.ws.rs.core.Application;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import static org.junit.Assert.*;

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
    public void testCreateListGet() {
    	
    	
        final String json = target().path("/tasks").request().get(String.class);
        
        assertEquals("[]", json);
    }
}
