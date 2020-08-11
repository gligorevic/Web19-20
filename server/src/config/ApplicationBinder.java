package config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import service.UserService;


public class ApplicationBinder extends AbstractBinder {
	
	@Override
	protected void configure() {
		bindAsContract(UserService.class);
	}

}
