package services;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Player;
import repositories.PlayerRepository;
import webcontrollers.PlayerController;

@Stateless
@LocalBean
public class PlayerService {
	
	@Inject
	PlayerRepository repo;
	
//	@PersistenceContext
//	EntityManager em;
	
	
	public void createPlayer(Player player){
		repo.save(player);
		//em.persist(player);
	}

}
