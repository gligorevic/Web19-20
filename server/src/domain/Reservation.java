package domain;

import java.util.Date;

import domain.interfaces.iIdentifiable;
import dto.ReservationDTO;

enum ReservationStatus {
	CREATED, REJECTED, WITHDRAWN, ACCEPTED, FINISHED
}

public class Reservation implements iIdentifiable<Long> {
	private Long id;
	private Apartment reservedApartment;
	private Date startReservationDate;
	private int nightsNum;
	private float price;
	private String reservationMessage;
	private User guest;
	private ReservationStatus reservationStatus;
	private Boolean deleted;
	
	public Reservation() {
		
	}
	
	public Reservation(ReservationDTO newRes) {
		this.nightsNum = newRes.getNightsNum();
		this.price = newRes.getPrice();
		this.reservationMessage = newRes.getReservationMessage();
	}
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Apartment getReservedApartment() {
		return reservedApartment;
	}

	public void setReservedApartment(Apartment reservedApartment) {
		this.reservedApartment = reservedApartment;
	}

	public Date getStartReservationDate() {
		return startReservationDate;
	}

	public void setStartReservationDate(Date startReservationDate) {
		this.startReservationDate = startReservationDate;
	}

	public int getNightsNum() {
		return nightsNum;
	}

	public void setNightsNum(int nightsNum) {
		this.nightsNum = nightsNum;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getReservationMessage() {
		return reservationMessage;
	}

	public void setReservationMessage(String reservationMessage) {
		this.reservationMessage = reservationMessage;
	}

	public User getGuest() {
		return guest;
	}

	public void setGuest(User guest) {
		this.guest = guest;
	}

	public ReservationStatus getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(ReservationStatus reservationStatus) {
		this.reservationStatus = reservationStatus;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	@Override
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
}
