package repository;

import domain.Amenity;
import repository.interfaces.iAmenityRepository;
import repository.interfaces.iIdGenerator;
import repository.interfaces.iStream;

public class AmenityRepository extends UniversalRepository<Amenity, Long> implements iAmenityRepository<Amenity, Long>{
	public AmenityRepository(iStream<Amenity> stream, iIdGenerator<Long> idGenerator) {
		super(stream, idGenerator);
	}
}
