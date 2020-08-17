package service;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import domain.User;
import dto.UserDTO;
import exception.CustomException;
import repository.LongIdGenerator;
import repository.UserRepository;
import repository.streams.JSONFileStream;


public class UserService {
	private UserRepository userRepository;

	public UserService() {
		JSONFileStream<User> jsonFileStream = new JSONFileStream<User>("users.txt", User.class);
		LongIdGenerator lidgen = new LongIdGenerator();
		lidgen.initializeId(Long.valueOf(jsonFileStream.readAll().size()));
		this.userRepository = new UserRepository(jsonFileStream, lidgen);
	}
	
	public List<User> findAll() {
		return userRepository.findAll();
	}

	public UserDTO register(UserDTO newUser) throws CustomException {
		if(userRepository.findByUsername(newUser.getUsername()) != null) throw new CustomException(Status.BAD_REQUEST);
		userRepository.save(new User(newUser));
		
		return newUser;
		
	}
	
}
