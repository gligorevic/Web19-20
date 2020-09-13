package repository;

import domain.User;
import repository.streams.JSONFileStream;

public class DBRepository {
	private UserRepository userRepository;
	
	public DBRepository() {
		JSONFileStream<User> jsonFileStream = new JSONFileStream<User>("users.txt", User.class);
		LongIdGenerator lidgen = new LongIdGenerator();
		lidgen.initializeId(Long.valueOf(jsonFileStream.readAll().size()));
		this.userRepository = new UserRepository(jsonFileStream, lidgen);
	}
	
	public UserRepository getUserRepository() {
		return this.userRepository;
	}
}
