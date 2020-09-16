package service;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

import domain.Amenity;
import exception.CustomException;
import repository.DBRepository;

public class AmenityService {

	@Inject
	private DBRepository db;
	
	public List<Amenity> getAllAmenities() {
		return db.getAmenityRepository().findAll().stream().filter(a -> !a.getDeleted()).collect(Collectors.toList());
	}

	public Amenity createAmenity(Amenity amenity) throws CustomException {
		if(db.getAmenityRepository().findAll().stream().filter(a -> a.getName().equals(amenity.getName()) && !a.getDeleted()).collect(Collectors.toList()).size() > 0) throw new CustomException("Amenity with given name already exist",Status.BAD_REQUEST);
		return db.getAmenityRepository().save(amenity);
	}

	public Amenity getAmenityById(Long id) {
		Amenity a = db.getAmenityRepository().findById(id);
		return  a.getDeleted() ? null : a;
	}

	public Amenity editAmenityById(Long id, Amenity amenity) throws CustomException {
		if(db.getAmenityRepository().findAll().stream().filter(a -> a.getName().equals(amenity.getName()) && !a.getDeleted()).collect(Collectors.toList()).size() > 0) throw new CustomException("Amenity with given name already exist",Status.BAD_REQUEST);
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
