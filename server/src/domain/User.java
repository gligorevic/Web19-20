package domain;

import domain.interfaces.iIdentifiable;

enum Gender {
	MALE, FEMALE, OTHER
}

enum Role {
	ADMIN, HOST, GUEST
}

public class User implements iIdentifiable<Long> {
	private Long id;
	private String username;
	private String password;
	private String name;
	private String lastName;
	private Gender gender;
		
	private Boolean deleted = false;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Boolean getDeleted() {
		return this.deleted;
	}
	
	@Override
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

}
