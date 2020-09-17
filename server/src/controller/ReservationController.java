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
import dto.ReservationDTO;
import exception.CustomException;
import security.Secured;
import service.ReservationService;

@Path("/reservations")
public class ReservationController {
	@Inject
	private ReservationService reservationService;
	
	
	@GET
	@Secured({ Role.GUEST, Role.HOST, Role.ADMIN  })
	@Produces(MediaType.APPLICATION_JSON)
	public Response gettAllReservations(@HeaderParam("Authorization") String token) {
		try {
			return Response.ok().entity(reservationService.getAllReservations(token)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
	@POST
	@Secured({Role.GUEST})
	@Produces(MediaType.APPLICATION_JSON)
	public Response createReservation(ReservationDTO newReservation ,  @HeaderParam("Authorization") String token) {
		try {
			if (!reservationHasRequiredFields(newReservation))
				return Response.status(Status.BAD_REQUEST).entity("All fields must be filled out.").build();
			return Response.ok().entity(reservationService.createNewReservation(newReservation)).build();
		}catch(Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
	private boolean reservationHasRequiredFields(ReservationDTO resDTO) {
		if (resDTO.getApartmentId() == null || resDTO.getStartReservationDate() == null
				|| resDTO.getGuestId() == null || resDTO.getNightsNum() == null)
			return false;
		return true;
	}
	
	@PUT
	@Secured({Role.GUEST,Role.HOST})
	@Path("/{id}/status")
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeReservationStatus(String status,@PathParam("id") Long id ,  @HeaderParam("Authorization") String token) {
		try {
		return Response.ok().entity(reservationService.changeReservationStatus(status, id, token)).build();
		}catch (CustomException e) {
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	
	}
}