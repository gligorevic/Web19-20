package repository.interfaces;

import domain.interfaces.iIdentifiable;

public interface iAmenityRepository<E extends iIdentifiable<ID>, ID> extends iRepository<E, ID> {
}
