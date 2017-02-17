package repositories;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@ApplicationScoped
public class EntityManagerProducer {
	
	@PersistenceUnit
	EntityManagerFactory emf;
	
	@Produces
	@Default
	@RequestScoped
	public EntityManager createEntityManager(){
		return emf.createEntityManager();
	}
	
	public void close(@Disposes @Default EntityManager em){
		if(em.isOpen()){
			em.close();
		}	
	}

}
