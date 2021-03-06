package domain;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import domain.interfaces.iIdentifiable;
	
public class Apartment implements iIdentifiable<Long> {
	private Long id;
	private String name;
	private Type type;
	private int roomNumber;
	private int guestNumber;
	private Location location;
	private Date startDate;
	private Date endDate;
	private User host;
	private List<String> images;
	private float pricePerNight;
	private Date checkInTime;
	private Date checkOutTime;
	private Status status;
	private Boolean deleted = false;
	private List<Amenity> amenities;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
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

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
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

	public Date getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(Date checkInTime) {
		this.checkInTime = checkInTime;
	}

	public Date getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(Date checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Amenity> getAmenities() {
		return amenities;
	}

	public void setAmenities(List<Amenity> amenities) {
		this.amenities = amenities;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	@Override
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public void changeObject(Apartment apartment) {
		this.name = apartment.getName();
		this.type = apartment.getType();
		this.roomNumber = apartment.getRoomNumber();
		this.guestNumber = apartment.getGuestNumber();
		this.location = apartment.getLocation();
		this.startDate = apartment.getStartDate();
		this.endDate = apartment.getEndDate();
		this.pricePerNight = apartment.getPricePerNight();
		this.checkInTime = apartment.getCheckInTime();
		this.checkOutTime = apartment.getCheckOutTime();
		this.amenities = apartment.getAmenities();
	}

}
