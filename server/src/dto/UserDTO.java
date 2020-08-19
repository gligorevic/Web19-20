package dto;

import domain.Gender;
import domain.Role;
import domain.User;

public class UserDTO {
	private String username;
	private String password;
	private String name;
	private String lastName;
	private Gender gender;
	private Role role = Role.GUEST;
	
	public UserDTO() {
	}
	
	public UserDTO(String username, String password, String name, String lastName, Gender gender) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.gender = gender;
	}



	public UserDTO(User user) {
		this.name = user.getName();
		this.lastName = user.getLastName();
		this.gender = user.getGender();
		this.role = user.getRole();
		this.username = user.getUsername();	
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

	

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserDTO [username=" + username + ", password=" + password + ", name=" + name + ", lastName=" + lastName
				+ ", gender=" + gender + "]";
	}
	
	
}
