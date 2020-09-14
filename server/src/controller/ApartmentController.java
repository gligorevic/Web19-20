package controller;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import domain.Apartment;
import domain.Role;
import exception.CustomException;
import security.Secured;
import service.ApartmentService;

@Path("/apartment")
public class ApartmentController {
	
	@Inject
	private ApartmentService apartmentService;
	
	@GET
	@Secured({ Role.HOST })
	@Path("/user/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserApartments(@PathParam("id") Long id, @HeaderParam("Authorization") String token) {
		try {
			return Response.ok().entity(apartmentService.getUserApartments(id, token)).build();
		} catch (CustomException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

	}
	
	@POST
	@Secured({ Role.HOST })
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response createApartmenet(@FormDataParam("apartment") Apartment apartment, @FormDataParam("file") List<FormDataBodyPart> files, @HeaderParam("Authorization") String token) {
		try {
			return Response.ok().entity(apartmentService.createApartment(apartment, files, token)).build();
		}  catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

	}
}
