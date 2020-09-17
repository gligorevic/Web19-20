package repository;

import java.util.ArrayList;
import java.util.List;

import domain.User;
import repository.interfaces.iIdGenerator;
import repository.interfaces.iStream;
import repository.interfaces.iUserRepository;

public class UserRepository extends UniversalRepository<User, Long> implements iUserRepository<User, Long> {

	public UserRepository(iStream<User> stream, iIdGenerator<Long> idGenerator) {
		super(stream, idGenerator);
	}

	public User findByUsername(String username) {
		List<User> users = new ArrayList<User>(findAll());
		for (User user : users) {
			if(username.equals(user.getUsername())) return user;
		}
		return null;
	}

}
