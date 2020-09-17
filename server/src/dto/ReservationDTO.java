package dto;

public class ReservationDTO {
	
	private Long apartmentId;
	private Long guestId;
	private Integer nightsNum;
	private float price;
	private String reservationMessage;
	private String reservationStatus;
	private String startReservationDate;
	
	public ReservationDTO() {
		
	}


	public Long getApartmentId() {
		return apartmentId;
	}


	public void setApartmentId(Long apartmentId) {
		this.apartmentId = apartmentId;
	}


	public String getStartReservationDate() {
		return startReservationDate;
	}


	public void setStartReservationDate(String startReservationDate) {
		this.startReservationDate = startReservationDate;
	}


	public Integer getNightsNum() {
		return nightsNum;
	}


	public void setNightsNum(Integer nightsNum) {
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


	public Long getGuestId() {
		return guestId;
	}


	public void setGuestId(Long guestId) {
		this.guestId = guestId;
	}


	public String getReservationStatus() {
		return reservationStatus;
	}


	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	
	
}
