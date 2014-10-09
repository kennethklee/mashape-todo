package com.kenneth.todo.rest.resource;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.kenneth.todo.rest.resource.TaskResource;

public class TaskResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(TaskResource.class)
        	.register(new MoxyJsonConfig().resolver());
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetIt() {
        final String responseMsg = target().path("/v1/tasks").request().get(String.class);

        assertEquals("[]", responseMsg);
    }
}
