package repository.interfaces;

import java.util.List;

import domain.interfaces.iIdentifiable;

public interface iCommentRepository<E extends iIdentifiable<ID>, ID> extends iRepository<E, ID> {
	List<E> findCommentsByApartmentId(Long id);
}
