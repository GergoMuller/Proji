package services;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import entities.Player;
import entities.User;
import repositories.PlayerRepository;
import repositories.UserRepository;

@Stateless
@LocalBean
public class PlayerService {
	
	@Inject
	PlayerRepository playerRepo;
	@Inject
	UserRepository userRepo;
	
	public void createPlayer(Player player){
		playerRepo.save(player);
		
	}
	
	public List<String> getByAge(int age){
		return playerRepo.findByAgeGreaterThanEqualsOrderByNameAsc(age)
								.stream()
								.map(x -> x.getName())
								.collect(Collectors.toList());
	}
	
	public boolean isEmailExists(String email){
		try{
		userRepo.findByEmail(email);
		}catch (NoResultException e ){
			return false;
		}
		return true;
	}
	
	public Player getPlayerByEmail(String email){
		return playerRepo.findByEmail(email);
		
	}

}
