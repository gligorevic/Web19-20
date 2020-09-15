package domain;

import domain.interfaces.iIdentifiable;

public class Amenity implements iIdentifiable<Long> {
	private Long id;
	private String name;
	private Boolean deleted = false;
	
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

	public Boolean getDeleted() {
		return deleted;
	}

	@Override
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
}
