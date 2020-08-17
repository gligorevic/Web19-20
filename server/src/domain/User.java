package domain;

import domain.interfaces.iIdentifiable;
import dto.UserDTO;

public class User implements iIdentifiable<Long> {
	private Long id;
	private String username;
	private String password;
	private String name;
	private String lastName;
	private Gender gender;
	private Role role;
	private Boolean deleted = false;
	
	public User() {
	}

	public User(Long id, String username, String password, String name, String lastName, Gender gender,
			Boolean deleted, Role role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.gender = gender;
		this.deleted = deleted;
		this.role = role;
	}



	public User(UserDTO newUser) {
		this.username = newUser.getUsername();
		this.password = newUser.getPassword();
		this.name = newUser.getName();
		this.lastName = newUser.getLastName();
		this.gender = newUser.getGender();
		this.role = newUser.getRole();
	}

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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	

}
