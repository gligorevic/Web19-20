package service;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import domain.Amenity;
import repository.DBRepository;

public class AmenityService {

	@Inject
	private DBRepository db;
	
	public List<Amenity> getAllAmenities() {
		return db.getAmenityRepository().findAll().stream().filter(a -> !a.getDeleted()).collect(Collectors.toList());
	}

	public Amenity createAmenity(Amenity amenity) {
		return db.getAmenityRepository().save(amenity);
	}

	public Amenity getAmenityById(Long id) {
		Amenity a = db.getAmenityRepository().findById(id);
		return  a.getDeleted() ? null : a;
	}

	public Amenity editAmenityById(Long id, Amenity amenity) {
		db.getAmenityRepository().update(amenity);
		return amenity;
	}

	public Amenity deleteAmenityById(Long id) {
		Amenity amenity = db.getAmenityRepository().findById(id);
		amenity.setDeleted(true);
		db.getAmenityRepository().update(amenity);
		return amenity;
	}

}
