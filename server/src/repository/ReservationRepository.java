package repository;

import domain.Reservation;
import repository.interfaces.iIdGenerator;
import repository.interfaces.iReservationRepository;
import repository.interfaces.iStream;

public class ReservationRepository extends UniversalRepository<Reservation, Long> implements iReservationRepository<Reservation, Long> {

	public ReservationRepository(iStream<Reservation> stream, iIdGenerator<Long> idGenerator) {
		super(stream, idGenerator);
	}

}
