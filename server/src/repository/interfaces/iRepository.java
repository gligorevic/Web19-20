package repository.interfaces;

import java.util.List;

import domain.interfaces.iIdentifiable;

public interface iRepository<E extends iIdentifiable<ID>,ID>  {
	E findById(ID id);
	List<E> findAll();
	E save(E entity);
	void update(E entity);
	void delete(E entity);
	void logicalDelete(E entity);
}
