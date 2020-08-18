package app;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import config.ApplicationBinder;
import controller.UserController;
import security.AuthenticationFilter;


@ApplicationPath("/api")
public class WebApp extends ResourceConfig {
	public WebApp() {
		register(UserController.class);
		register(new ApplicationBinder());
		register(AuthenticationFilter.class);
    }
	
}
