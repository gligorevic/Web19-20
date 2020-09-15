package controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import service.AmenityService;

@Path("/amenity")
public class AmenityController {
	@Inject
	private AmenityService amenityService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllAmenities() {
		try {
			return Response.ok().entity(amenityService.getAllAmenities()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

	}
}
