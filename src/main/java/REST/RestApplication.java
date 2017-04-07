package REST;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

@ApplicationPath("/sm")
public class RestApplication extends Application {
	
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> classes = new HashSet<>();

	public RestApplication() {
		CorsFilter corsFilter = new CorsFilter();
		corsFilter.getAllowedOrigins().add("*");
        corsFilter.setAllowedMethods("OPTIONS, GET, POST, DELETE, PUT, PATCH");
        singletons.add(corsFilter);
        
        classes.add(PlayerRest.class);
		classes.add(TeamRest.class);
	}
	
	@Override
	public Set<Class<?>> getClasses() {
			return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		
		return singletons;
	}
	
	
	
	

}
