package repository.interfaces;

import domain.interfaces.iIdentifiable;

public interface iUserRepository<E extends iIdentifiable<ID>, ID> extends iRepository<E, ID> {

}
