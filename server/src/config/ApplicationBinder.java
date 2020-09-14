package config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import service.ApartmentService;
import service.UserService;
import repository.DBRepository;

public class ApplicationBinder extends AbstractBinder {
	
	@Override
	protected void configure() {
		bindAsContract(DBRepository.class);
		bindAsContract(UserService.class);
		bindAsContract(ApartmentService.class);
	}

}
