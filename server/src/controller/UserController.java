package controller;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import domain.Role;
import dto.LoginDTO;
import dto.UserDTO;
import exception.CustomException;
import security.Secured;
import service.UserService;

@Path("/user")
public class UserController {
	@Inject
	private UserService userService;
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String test() {		
		return userService.findAll().toString();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(UserDTO newUser) {
		try {
			if(!hasRequiredFields(newUser)) return Response.status(Status.BAD_REQUEST).entity("All fields must be filled out.").build();
			return Response.ok().entity(userService.register(newUser)).build();		
		} catch(CustomException e){
			return Response.status(Status.BAD_REQUEST).entity("User with given username already exists.").build();
		} catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		
	}

	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(LoginDTO authData) {
		try {
			return Response.ok().entity(userService.login(authData)).build();		
		} catch(CustomException e){
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		} catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		
	}
	
	@GET
	@Secured({Role.ADMIN, Role.GUEST})
	@Path("/hello")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sayHello() {
		try {
			return Response.ok().entity("Hello").build();		
		} catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		
	}
	
	private boolean hasRequiredFields(UserDTO newUser) {
		if(newUser.getUsername().isEmpty() || newUser.getPassword().isEmpty() || newUser.getName().isEmpty() || newUser.getLastName().isEmpty()) 
			return false;	
		return true;
	}
}
