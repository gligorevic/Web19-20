package repository;

import domain.User;
import repository.interfaces.iIdGenerator;
import repository.interfaces.iStream;
import repository.interfaces.iUserRepository;

public class UserRepository extends UniversalRepository<User, Long> implements iUserRepository<User, Long> {

	public UserRepository(iStream<User> stream, iIdGenerator<Long> idGenerator) {
		super(stream, idGenerator);
	}

}
