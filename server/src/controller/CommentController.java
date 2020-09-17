package controller;

import javax.inject.Inject;
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
import dto.CommentDTO;
import exception.CustomException;
import security.Secured;
import service.CommentService;

@Path("/comments")
public class CommentController {
	@Inject
	private CommentService commentService;

	
	
	@PUT
	@Path("/{id}/status")
	@Secured({Role.HOST})
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeCommentStatus(@PathParam("id") Long id ,@HeaderParam("Authorization") String token){
		try {
			return Response.ok().entity(commentService.changeCommentStatus(id)).build();
		}catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/{id}")
	@Secured({Role.ADMIN,Role.GUEST,Role.HOST})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllComments(@PathParam("id") Long id ,@HeaderParam("Authorization") String token) {
		try {
			System.out.println("BEENT HEER");
			return Response.ok().entity(commentService.getAllComments(id ,token)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
	
	@POST
	@Secured({Role.GUEST})
	@Produces(MediaType.APPLICATION_JSON)
	public Response createNewComment(CommentDTO newComment,@HeaderParam("Authorization") String token){
		try {
			if (!hasRequiredFields(newComment))
				return Response.status(Status.BAD_REQUEST).entity("All fields must be filled out.").build();
			return Response.ok().entity(commentService.createNew(newComment, token)).build();
		}catch (CustomException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
	private boolean hasRequiredFields(CommentDTO newComment) {
		if (newComment.getText().isEmpty() || newComment.getGrade()==0)
			return false;
		return true;
	}
	
}
