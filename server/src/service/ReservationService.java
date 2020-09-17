package service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

import config.SecurityConstants;
import domain.Apartment;
import domain.Reservation;
import domain.ReservationStatus;
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
	
	public List<Date> getAllDatesForApartment(Long id , String token) {
		List<Reservation> reservations = db.getReservationRepository().findReservationsForApartment(id).stream().filter(r -> r.getReservationStatus() == ReservationStatus.ACCEPTED).collect(Collectors.toList());
		List<Date> dates = new ArrayList<Date>();
		
		for(Reservation res : reservations) {
			for(int i = 0 ; i < res.getNightsNum(); i++) {
				Calendar c = Calendar.getInstance();
				c.setTime(res.getStartReservationDate());
				c.add(Calendar.DATE, i);
				dates.add(c.getTime());
			}
				
		}
		
		return dates;
		
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
		if(checkReservationDate(newReservation, reservation.getStartReservationDate())) {
			throw new CustomException("You have selected dates that are not available.", Status.BAD_REQUEST);
		}
		
		
		reservation.setReservedApartment(db.getApartmentRepository().findById(newReservation.getApartmentId()));
		reservation.setGuest(db.getUserRepository().findById(newReservation.getGuestId()));
		
		Reservation saved = db.getReservationRepository().save(reservation);
		saved.setGuest(null);
		saved.getReservedApartment().setHost(null);
		return saved;
		
	}
	
	private Boolean checkReservationDate(ReservationDTO newReservation , Date date) {
		List<Reservation> reservations = db.getReservationRepository()
										.findReservationsForApartment(newReservation.getApartmentId())
										.stream()
										.filter(r -> r.getReservationStatus() == ReservationStatus.ACCEPTED)
										.collect(Collectors.toList());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		List<String> datesOccupied = new ArrayList<String>();
		
		for(Reservation res : reservations) {
			for(int i = 0 ; i < res.getNightsNum(); i++) {
				Calendar c = Calendar.getInstance();
				c.setTime(res.getStartReservationDate());
				c.add(Calendar.DATE, i);
				try {
					String s = formatter.format(c.getTime());
					datesOccupied.add(s);
				}catch(Exception e) {
						e.printStackTrace();
						System.out.println("Date formatting failed.");
			}
		}
	}		
		for(int j = 0 ; j < newReservation.getNightsNum() ; j++) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, j );
			try {
				String s = formatter.format(cal.getTime());
				if(datesOccupied.contains(s))
					return true;
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("Date formatting failed.");
			}
		}
		
		
	return false;
	}
	
}
