package controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
}
