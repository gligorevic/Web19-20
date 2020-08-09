package repository.interfaces;

import java.util.List;

public interface iStream<E> {
	void saveAll(List<E> entities);
	List<E> readAll();
	void appendToFile(E entity);
}
