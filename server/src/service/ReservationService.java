package service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

import config.SecurityConstants;
import domain.Apartment;
import domain.Reservation;
import domain.Role;
import domain.User;
import dto.ApartmentCardDTO;
import dto.ReservationDTO;
import exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import repository.DBRepository;

public class ReservationService {
	@Inject
	private DBRepository db;
	@Inject
	private ApartmentService apartmentService;
	@Inject
	private UserService userService;
	
	
	public List<Reservation> getAllReservations(String token) throws CustomException{
		
		
		Long userId = userService.getIdFromJWT(token);
		List<Long> apartmetnIds = new ArrayList<Long>();
		for(ApartmentCardDTO acd : apartmentService.getUserApartments(userId, token)) {
			apartmetnIds.add(acd.getId());
		}
		User user = db.getUserRepository().findById(userId);
		
		if(user.getRole() ==  Role.ADMIN) {
			return db.getReservationRepository().findAll();
		}else if (user.getRole() == Role.GUEST) {
			return db.getReservationRepository().findReservationsByUserId(userId);
		}else {
			return db.getReservationRepository().findReservationsByUserApartment(apartmetnIds);
		}
	}
	
	public Reservation changeReservationStatus(String status,Long id,String token) throws CustomException {
		Reservation reservation = db.getReservationRepository().findById(id);
		
		if (reservation == null)
			throw new CustomException("Unauthorized", Status.UNAUTHORIZED);
		
		if (!(status.equals("WITHDRAWN")) && userService.getIdFromJWT(token) != reservation.getReservedApartment().getHost().getId()) 
				throw new CustomException("Unauthorized", Status.UNAUTHORIZED);
		
		
		if(status.equals("WITHDRAWN") && userService.getIdFromJWT(token) != reservation.getGuest().getId())
			throw new CustomException("Unauthorized", Status.UNAUTHORIZED);
		
		
		reservation.setReservationStatus(status);
		db.getReservationRepository().update(reservation);
		return reservation;
	}
		
	
	public Reservation createNewReservation(ReservationDTO newReservation) throws CustomException{
		
		Reservation reservation = new Reservation(newReservation);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
		reservation.setStartReservationDate(formatter.parse(newReservation.getStartReservationDate()));
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Date formatting failed.");
		}
		reservation.setReservedApartment(db.getApartmentRepository().findById(newReservation.getApartmentId()));
		
		reservation.setGuest(db.getUserRepository().findById(newReservation.getGuestId()));
		
		Reservation saved = db.getReservationRepository().save(reservation);
		saved.setGuest(null);
		saved.getReservedApartment().setHost(null);
		return saved;
		
	}
	
}
