package app;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import config.ApplicationBinder;
import controller.AmenityController;
import controller.ApartmentController;
import controller.CommentController;
import controller.ImageController;
import controller.ReservationController;
import controller.UserController;
import security.AuthenticationFilter;


@ApplicationPath("/api")
public class WebApp extends ResourceConfig {
	public WebApp() {
		register(MultiPartFeature.class);
		register(UserController.class);
		register(ApartmentController.class);
		register(ImageController.class);
		register(AmenityController.class);
		register(new ApplicationBinder());
		register(AuthenticationFilter.class);
		register(ReservationController.class);
		register(CommentController.class);
		
    }
	
}
