package service;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import domain.Apartment;
import domain.Reservation;
import dto.ReservationDTO;
import exception.CustomException;
import repository.DBRepository;

public class ReservationService {
	@Inject
	private DBRepository db;
	
	public Reservation createNewReservation(ReservationDTO newReservation) throws CustomException{
		
		Reservation reservation = db.getReservationRepository().save(new Reservation(newReservation));
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
		reservation.setStartReservationDate(formatter.parse(newReservation.getStartReservationDate()));
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Date formatting failed.");
		}
		////get Apartment and user from DB
		reservation.setReservedApartment(new Apartment());
		reservation.setGuest(db.getUserRepository().findById(newReservation.getGuestId()));
		
		Reservation saved = db.getReservationRepository().save(reservation);
		
		return saved;
		
	}
	
}
