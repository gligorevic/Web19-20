package dto;

import java.util.List;

import domain.Address;
import domain.Apartment;
import domain.Status;
import domain.Type;

public class ApartmentCardDTO {
	private Long id;
	private String name;
	private Type type;
	private int roomNumber;
	private int guestNumber;
	private List<String> images;
	private float pricePerNight;
	private Address address;
	private Status status;
	
	public ApartmentCardDTO() {
	}

	public ApartmentCardDTO(Apartment a) {
		this.id = a.getId();
		this.name = a.getName();
		this.type = a.getType();
		this.roomNumber = a.getRoomNumber();
		this.guestNumber = a.getGuestNumber();
		this.images = a.getImages();
		this.pricePerNight = a.getPricePerNight();
		if(a.getLocation() != null) this.address = a.getLocation().getAddress();
		this.status = a.getStatus();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public int getGuestNumber() {
		return guestNumber;
	}

	public void setGuestNumber(int guestNumber) {
		this.guestNumber = guestNumber;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public float getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(float pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
		
}
