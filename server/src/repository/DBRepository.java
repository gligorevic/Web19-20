package repository;

import domain.Amenity;
import domain.Apartment;
import domain.User;
import repository.streams.JSONFileStream;

public class DBRepository {
	private UserRepository userRepository;
	private ApartmentRepository apartmentRepository;
	private AmenityRepository amenityRepository;
	
	public DBRepository() {
		initUserRepository();
		initApartmentRepository();
		initAmenityRepository();
	}

	private void initAmenityRepository() {
		JSONFileStream<Amenity> jsonFileStream = new JSONFileStream<Amenity>("amenity.txt", Amenity.class);
		LongIdGenerator lidgen = new LongIdGenerator();
		lidgen.initializeId(Long.valueOf(jsonFileStream.readAll().size()));
		this.amenityRepository = new AmenityRepository(jsonFileStream, lidgen);	
	}

	private void initUserRepository() {
		JSONFileStream<User> jsonFileStream = new JSONFileStream<User>("users.txt", User.class);
		LongIdGenerator lidgen = new LongIdGenerator();
		lidgen.initializeId(Long.valueOf(jsonFileStream.readAll().size()));
		this.userRepository = new UserRepository(jsonFileStream, lidgen);
	}
	
	private void initApartmentRepository() {
		JSONFileStream<Apartment> jsonFileStream = new JSONFileStream<Apartment>("apartments.txt", Apartment.class);
		LongIdGenerator lidgen = new LongIdGenerator();
		lidgen.initializeId(Long.valueOf(jsonFileStream.readAll().size()));
		this.apartmentRepository = new ApartmentRepository(jsonFileStream, lidgen);		
	}
	
	public UserRepository getUserRepository() {
		return this.userRepository;
	}
	
	public ApartmentRepository getApartmentRepository() {
		return this.apartmentRepository;
	}
	
	public AmenityRepository getAmenityRepository() {
		return this.amenityRepository;
	}
}
