package controller;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


import dto.ReservationDTO;
import dto.UserDTO;
import service.ReservationService;

@Path("/reservations")
public class ReservationController {
	@Inject
	private ReservationService reservationService;
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createReservation(ReservationDTO newReservation) {
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
	
}
