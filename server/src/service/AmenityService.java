package service;

import java.util.List;

import javax.inject.Inject;

import domain.Amenity;
import repository.DBRepository;

public class AmenityService {

	@Inject
	private DBRepository db;
	
	public List<Amenity> getAllAmenities() {
		return db.getAmenityRepository().findAll();
	}

}
