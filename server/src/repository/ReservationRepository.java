package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import domain.Reservation;
import dto.ApartmentCardDTO;
import repository.interfaces.iIdGenerator;
import repository.interfaces.iReservationRepository;
import repository.interfaces.iStream;

public class ReservationRepository extends UniversalRepository<Reservation, Long> implements iReservationRepository<Reservation, Long> {

	public ReservationRepository(iStream<Reservation> stream, iIdGenerator<Long> idGenerator) {
		super(stream, idGenerator);
	}

	@Override
	public List<Reservation> findReservationsByUserId(Long id) {
		List<Reservation> reservations = new ArrayList<Reservation>(findAll());
		return reservations.stream().filter(a -> a.getGuest().getId() == id)
				.collect(Collectors.toList());
				
	}

	@Override
	public List<Reservation> findReservationsByUserApartment(List<Long> apartmentIds) {
		List<Reservation> reservations = new ArrayList<Reservation>(findAll());
		
		return reservations.stream()
				.filter(a -> apartmentIds.contains(a.getReservedApartment().getId()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Reservation> findReservationsForApartment(Long apartmentId) {
		List<Reservation> reservations = new ArrayList<Reservation>(findAll());
		
		return reservations.stream()
				.filter(a -> a.getReservedApartment().getId() == apartmentId)
				.collect(Collectors.toList());
	}

}
