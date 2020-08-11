package app;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import config.ApplicationBinder;
import controller.UserController;


@ApplicationPath("/api")
public class WebApp extends ResourceConfig {
	public WebApp() {
		register(UserController.class);
		register(new ApplicationBinder());
    }
	
}
