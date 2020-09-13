package repository.interfaces;

import java.util.List;

import domain.interfaces.iIdentifiable;

public interface iApartmentRepository<E extends iIdentifiable<ID>, ID> extends iRepository<E, ID> {
	List<E> findApartmentsByUserId(Long id);
}
