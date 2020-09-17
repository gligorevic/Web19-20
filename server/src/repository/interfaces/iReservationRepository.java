package repository.interfaces;

import java.util.List;

import domain.interfaces.iIdentifiable;
import dto.ApartmentCardDTO;

public interface iReservationRepository<E extends iIdentifiable<ID>, ID> extends iRepository<E, ID> {
	List<E> findReservationsByUserId(Long id);
	
	List<E> findReservationsByUserApartment(List<Long> apartmentIds);
}
