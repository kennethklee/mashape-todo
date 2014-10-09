package com.kenneth.todo.rest.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.kenneth.todo.dao.TaskDao;
import com.kenneth.todo.dao.TaskInMemoryDao;

/**
 * Task resource.
 * Exposed at /tasks.
 */
@Path("/tasks")
public class TaskResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
    	// TODO Refactor out and made into singleton or factory or something
    	// TODO Use service layer instead of directly interfacing with dao
    	
    	TaskDao dao = new TaskInMemoryDao();
    	
        return Response.ok().entity(dao.findAll()).build();
    }
}
