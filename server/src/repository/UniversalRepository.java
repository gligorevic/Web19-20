package repository;

import java.util.List;

import domain.interfaces.iIdentifiable;
import repository.interfaces.iIdGenerator;
import repository.interfaces.iRepository;
import repository.interfaces.iStream;

public class UniversalRepository<E extends iIdentifiable<ID>,ID> implements iRepository<E,ID> {
	private iStream<E> stream;	
	private iIdGenerator<ID> idGenerator;
	
	public UniversalRepository(iStream<E> stream, iIdGenerator<ID> idGenerator) {
		this.stream = stream;
		this.idGenerator = idGenerator;
	}

	@Override
	public E findById(ID id) {
		return stream.readAll().stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
	}

	@Override
	public List<E> findAll() {
		return stream.readAll();
	}

	@Override
	public E save(E entity) {
		entity.setId(idGenerator.generateId());
		stream.appendToFile(entity);
		return entity;
	}

	@Override
	public void update(E entity) {
		List<E> entities = stream.readAll();
		for(int i = 0; i < entities.size(); i++)
			if(entities.get(i).getId().equals(entity.getId())) {
				entities.set(i, entity);
				break;
			}
		stream.saveAll(entities);
	}

	@Override
	public void delete(E entity) {
		List<E> entities = stream.readAll();
		entities.remove(entity);
		stream.saveAll(entities);
	}

}
