package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import domain.Apartment;
import repository.interfaces.iApartmentRepository;
import repository.interfaces.iIdGenerator;
import repository.interfaces.iStream;

public class ApartmentRepository extends UniversalRepository<Apartment, Long> implements iApartmentRepository<Apartment, Long> {

	public ApartmentRepository(iStream<Apartment> stream, iIdGenerator<Long> idGenerator) {
		super(stream, idGenerator);
	}

	@Override
	public List<Apartment> findApartmentsByUserId(Long id) {
		List<Apartment> apartments = new ArrayList<Apartment>(findAll());
		return apartments.stream().filter(a -> a.getHost().getId()== id).map(a -> {a.setHost(null); return a;}).collect(Collectors.toList());
	}

}
