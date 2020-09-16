package controller;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import domain.Amenity;
import domain.Role;
import exception.CustomException;
import security.Secured;
import service.AmenityService;

@Path("/amenity")
public class AmenityController {
	@Inject
	private AmenityService amenityService;

	@GET
	@Secured({Role.ADMIN, Role.HOST})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllAmenities() {
		try {
			return Response.ok().entity(amenityService.getAllAmenities()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

	}
	
	@GET
	@Secured({Role.ADMIN})
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAmenity(@PathParam("id") Long id) {
		try {
			return Response.ok().entity(amenityService.getAmenityById(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

	}
	
	@PUT
	@Secured({Role.ADMIN})
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAmenity(@PathParam("id") Long id, Amenity amenity) {
		try {
			return Response.ok().entity(amenityService.editAmenityById(id, amenity)).build();
		} catch (CustomException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

	}
	
	
	@POST
	@Secured({Role.ADMIN})
	@Produces(MediaType.APPLICATION_JSON)
	public Response createAmenity(Amenity amenity) {
		try {
			return Response.ok().entity(amenityService.createAmenity(amenity)).build();
		} catch (CustomException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

	}
	
	@DELETE
	@Secured({Role.ADMIN})
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAmenity(@PathParam("id") Long id) {
		try {
			return Response.ok().entity(amenityService.deleteAmenityById(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

	}
}
