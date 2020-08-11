package domain.interfaces;

public interface iIdentifiable<T> {
	public T getId();
	public void setId(T id);
	public void setDeleted(Boolean deleted);
}
