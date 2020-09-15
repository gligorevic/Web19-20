package repository.interfaces;

import domain.interfaces.iIdentifiable;

public interface iReservationRepository<E extends iIdentifiable<ID>, ID> extends iRepository<E, ID> {

}
