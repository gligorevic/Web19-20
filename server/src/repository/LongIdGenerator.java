package repository;

import repository.interfaces.iIdGenerator;

public class LongIdGenerator implements iIdGenerator<Long>{
	private long nextId;

	@Override
	public void initializeId(Long initId) {
		this.nextId = initId;
	}

	@Override
	public Long generateId() {
		return nextId++;
	}
}
