package domain;

import domain.interfaces.iIdentifiable;

public class Location implements iIdentifiable<Long> {
	private Long id;
	private double longitude;
	private double latitude;
	private Address address;
	private Boolean deleted = false;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Boolean getDeleted() {
		return this.deleted;
	}
	
	@Override
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

}
