package com.kenneth.todo.rest.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.kenneth.todo.dao.TaskDao;
import com.kenneth.todo.factory.SingletonFactory;
import com.kenneth.todo.model.TaskModel;

/**
 * Task resource.
 * Exposed at /tasks.
 */
@Path("/tasks")
public class TaskResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TaskModel> list() {
    	// TODO Use service layer instead of directly interfacing with dao
    	TaskDao dao = SingletonFactory.getInstance().getTaskDao();
    	
        return dao.findAll();
    }
    
    @POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public TaskModel create(TaskModel model) {
		TaskDao dao = SingletonFactory.getInstance().getTaskDao();
		
		return dao.create(model);
	}
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TaskModel get(@PathParam("id") String id) {
    	TaskDao dao = SingletonFactory.getInstance().getTaskDao();
    	
    	TaskModel model = dao.get(id);
    	if (model == null) {
    		throw new NotFoundException();
    	}
		return model;
    }
    
}
