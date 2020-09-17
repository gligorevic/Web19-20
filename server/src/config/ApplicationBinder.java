package config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import repository.DBRepository;
import service.AmenityService;
import service.ApartmentService;
import service.CommentService;
import service.ReservationService;
import service.UserService;

public class ApplicationBinder extends AbstractBinder {
	
	@Override
	protected void configure() {
		bindAsContract(DBRepository.class);
		bindAsContract(UserService.class);
		bindAsContract(ApartmentService.class);
		bindAsContract(AmenityService.class);
		bindAsContract(ReservationService.class);
		bindAsContract(CommentService.class);
	}

}
