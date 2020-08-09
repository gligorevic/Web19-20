package repository.interfaces;

public interface iIdGenerator<T> {
	void initializeId(T initId);
	T generateId();
}
