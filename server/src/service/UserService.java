package service;

import java.util.List;

import domain.User;
import repository.LongIdGenerator;
import repository.UserRepository;
import repository.streams.JSONFileStream;


public class UserService {
	private UserRepository userRepository;

	public UserService() {
		JSONFileStream<User> jsonFileStream = new JSONFileStream<>("users.txt");
		LongIdGenerator lidgen = new LongIdGenerator();
		//lidgen.initializeId(Long.valueOf(jsonFileStream.readAll().size()));
		this.userRepository = new UserRepository(jsonFileStream, lidgen);
	}
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
}
