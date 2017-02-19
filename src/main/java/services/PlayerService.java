package services;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Player;
import repositories.PlayerRepository;

@Stateless
@LocalBean
public class PlayerService {
	
	@Inject
	PlayerRepository repo;
	
	public void createPlayer(Player player){
		repo.save(player);
		
	}
	
	public List<String> getByAge(int age){
		return repo.findByAgeGreaterThanEqualsOrderByNameAsc(age)
								.stream()
								.map(x -> x.getName())
								.collect(Collectors.toList());
	}

}
