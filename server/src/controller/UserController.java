package controller;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import domain.Role;
import dto.ChangePasswordDTO;
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
	@Secured({ Role.ADMIN, Role.HOST })
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {
		try {
			return Response.ok().entity(userService.findAll()).build();
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(UserDTO newUser) {
		try {
			if (!hasRequiredFields(newUser))
				return Response.status(Status.BAD_REQUEST).entity("All fields must be filled out.").build();
			return Response.ok().entity(userService.register(newUser)).build();
		} catch (CustomException e) {
			return Response.status(Status.BAD_REQUEST).entity("User with given username already exists.").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

	}
	
	@POST
	@Path("/host")
	@Secured({ Role.ADMIN })
	@Produces(MediaType.APPLICATION_JSON)
	public Response newHost(UserDTO newHost){
		try {
			if (!hasRequiredFields(newHost))
				return Response.status(Status.BAD_REQUEST).entity("All fields must be filled out.").build();
			return Response.ok().entity(userService.registerHost(newHost)).build();
		} catch (CustomException e) {
			return Response.status(Status.BAD_REQUEST).entity("User with given username already exists.").build();
		} catch (Exception e) {
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
		} catch (CustomException e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

	}


	private boolean hasRequiredFields(UserDTO newUser) {
		if (newUser.getUsername().isEmpty() || newUser.getPassword().isEmpty() || newUser.getName().isEmpty()
				|| newUser.getLastName().isEmpty())
			return false;
		return true;
	}

	@GET
	@Secured({ Role.GUEST, Role.HOST, Role.ADMIN })
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("id") Long id, @HeaderParam("Authorization") String token) {
		try {
			return Response.ok().entity(userService.getUser(id, token)).build();
		} catch (CustomException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

	}
	
	@PUT
	@Secured({ Role.GUEST, Role.HOST, Role.ADMIN  })
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editUser(UserDTO userData, @PathParam("id") Long id, @HeaderParam("Authorization") String token) {
		try {
			return Response.ok().entity(userService.editUser(userData, id, token)).build();
		} catch (CustomException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

	}
	
	@PUT
	@Secured({ Role.GUEST, Role.HOST, Role.ADMIN  })
	@Path("/{id}/password")
	@Produces(MediaType.APPLICATION_JSON)
	public Response changePassword(ChangePasswordDTO changePasswordDTO, @PathParam("id") Long id, @HeaderParam("Authorization") String token) {
		try {
			return Response.ok().entity(userService.changePassword(changePasswordDTO, id, token)).build();
		} catch (CustomException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}	
	}

}
