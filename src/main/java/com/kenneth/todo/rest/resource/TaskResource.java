package com.kenneth.todo.rest.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.kenneth.todo.factory.SingletonFactory;
import com.kenneth.todo.model.TaskModel;
import com.kenneth.todo.service.TaskService;

/**
 * Task resource.
 * Exposed at /tasks.
 */
@Path("/tasks")
public class TaskResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TaskModel> list() {
    	TaskService service = SingletonFactory.getInstance().getTaskService();
    	
        return service.list();
    }
    
    @POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Response create(TaskModel model) {
    	TaskService service = SingletonFactory.getInstance().getTaskService();

    	TaskModel createdModel = service.create(model);
		
		return Response.status(Status.CREATED)
				.entity(createdModel)
				.build();
	}
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TaskModel get(@PathParam("id") String id) {
    	TaskService service = SingletonFactory.getInstance().getTaskService();
    	
    	TaskModel model = service.get(id);
    	if (model == null) {
    		throw new NotFoundException();
    	}
		return model;
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TaskModel update(@PathParam("id") String id, TaskModel model) {
    	TaskService service = SingletonFactory.getInstance().getTaskService();

    	TaskModel targetModel = service.get(id);
    	if (targetModel == null) {
    		throw new NotFoundException();
    	}
    	
    	return service.update(id, model);
    }
    
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") String id) {
    	TaskService service = SingletonFactory.getInstance().getTaskService();
    	
    	TaskModel model = service.get(id);
    	if (model == null) {
    		throw new NotFoundException();
    	}
    	
    	service.delete(id);
    }
}
