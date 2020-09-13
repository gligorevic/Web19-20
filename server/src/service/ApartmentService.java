package service;

import java.util.List;

import javax.inject.Inject;

import domain.Apartment;
import exception.CustomException;
import repository.DBRepository;

public class ApartmentService {

	@Inject
	private UserService userService;
	
	@Inject
	private DBRepository db;
	
	public List<Apartment> getUserApartments(Long id, String token) throws CustomException {
		userService.getUser(id, token);
		return db.getApartmentRepository().findApartmentsByUserId(id);
	}

}
