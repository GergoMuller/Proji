package REST;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

@ApplicationPath("/sm")
public class RestApplication extends Application {
	
	private Set<Object> singletons = new HashSet<Object>();

	public RestApplication() {
		CorsFilter corsFilter = new CorsFilter();
		corsFilter.getAllowedOrigins().add("*");
        corsFilter.setAllowedMethods("OPTIONS, GET, POST, DELETE, PUT, PATCH");
        singletons.add(corsFilter);

	}
	
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(PlayerRest.class);
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		
		return singletons;
	}
	
	
	
	

}
